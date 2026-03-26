package com.bookingHotel.services.impls;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.BookingConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.bookings.BookingCreateDto;
import com.bookingHotel.dtos.bookings.BookingFindDto;
import com.bookingHotel.dtos.bookings.BookingResponseDto;
import com.bookingHotel.dtos.bookings.BookingUpdateDto;
import com.bookingHotel.repositories.BookingRepository;
import com.bookingHotel.repositories.RoomRepository;
import com.bookingHotel.repositories.UserRepository;
import com.bookingHotel.repositories.entities.BookingEntity;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.repositories.entities.UserEntity;
import com.bookingHotel.repositories.enums.BookingStatus;
import com.bookingHotel.repositories.enums.RoomStatus;
import com.bookingHotel.services.BookingService;
import com.bookingHotel.services.EmailService;

@Service
public class BookingServiceImpl implements BookingService {
  @Autowired
  private BookingConverter bookingConverter;

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private EmailService emailService;

  @Override
  public ResponseEntity<ResponseDto<BookingResponseDto>> create(BookingCreateDto bookingCreateDto) {
    UserEntity userEntity = this.userRepository.findById(bookingCreateDto.getUserId()).orElse(null);
    if (userEntity == null) {
      return ResponseDto.badRequest(List.of("User not found"));
    }

    RoomEntity roomEntity = this.roomRepository.findById(bookingCreateDto.getRoomId()).orElse(null);
    if (roomEntity == null) {
      return ResponseDto.badRequest(List.of("Room not found"));
    }

    boolean hasPendingBooking = this.bookingRepository.existsByUserIdAndRoomIdAndStatus(
        userEntity.getId(),
        roomEntity.getId(),
        BookingStatus.PENDING);
    if (hasPendingBooking) {
      return ResponseDto.badRequest(List.of("You already have a pending booking for this room"));
    }

    if (bookingCreateDto.getCheckIn().isAfter(bookingCreateDto.getCheckOut())) {
      return ResponseDto.badRequest(List.of("Check-in must be before check-out"));
    }

    List<BookingStatus> blockingStatuses = List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED);
    boolean hasConflict = this.bookingRepository.existsConflictingBooking(
        roomEntity.getId(),
        bookingCreateDto.getCheckIn(),
        bookingCreateDto.getCheckOut(),
        blockingStatuses);
    if (hasConflict) {
      return ResponseDto.badRequest(List.of("Room is already booked for the selected dates"));
    }

    BookingEntity bookingEntity = this.bookingConverter.toBookingEntity(bookingCreateDto);
    bookingEntity.setUser(userEntity);
    bookingEntity.setRoom(roomEntity);

    bookingEntity = this.bookingRepository.save(bookingEntity);

    try {
      if (bookingEntity.getStatus() == BookingStatus.CONFIRMED) {
        this.emailService.sendBookingQrConfirmation(bookingEntity);
      } else {
        this.emailService.sendBookingConfirmation(bookingEntity);
      }
    } catch (Exception ex) {
      System.out.println("Failed to send booking email: " + ex.getMessage());
    }

    BookingResponseDto bookingResponseDto = this.bookingConverter.toBookingResponseDto(bookingEntity);

    return ResponseDto.created(bookingResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<BookingResponseDto>> update(Long id, BookingUpdateDto bookingUpdateDto) {
    BookingEntity bookingEntity = this.bookingRepository.findById(id).orElse(null);
    if (bookingEntity == null) {
      return ResponseDto.notFound("Booking not found");
    }

    BookingStatus previousStatus = bookingEntity.getStatus();

    if (bookingUpdateDto.getUserId() != null) {
      UserEntity userEntity = this.userRepository.findById(bookingUpdateDto.getUserId()).orElse(null);
      if (userEntity == null) {
        return ResponseDto.badRequest(List.of("User not found"));
      }
      bookingEntity.setUser(userEntity);
    }

    RoomEntity targetRoom = bookingEntity.getRoom();
    if (bookingUpdateDto.getRoomId() != null) {
      RoomEntity roomEntity = this.roomRepository.findById(bookingUpdateDto.getRoomId()).orElse(null);
      if (roomEntity == null) {
        return ResponseDto.badRequest(List.of("Room not found"));
      }
      targetRoom = roomEntity;
      bookingEntity.setRoom(roomEntity);
    }

    LocalDate checkIn = bookingUpdateDto.getCheckIn() != null ? bookingUpdateDto.getCheckIn()
        : bookingEntity.getCheckIn();
    LocalDate checkOut = bookingUpdateDto.getCheckOut() != null ? bookingUpdateDto.getCheckOut()
        : bookingEntity.getCheckOut();
    if (checkIn != null && checkOut != null && checkIn.isAfter(checkOut)) {
      return ResponseDto.badRequest(List.of("Check-in must be before check-out"));
    }

    if (targetRoom != null && checkIn != null && checkOut != null) {
      List<BookingStatus> blockingStatuses = List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED);
      boolean hasConflict = this.bookingRepository.existsConflictingBookingExcludingId(
          targetRoom.getId(),
          bookingEntity.getId(),
          checkIn,
          checkOut,
          blockingStatuses);
      if (hasConflict) {
        return ResponseDto.badRequest(List.of("Room is already booked for the selected dates"));
      }
    }

    this.bookingConverter.copyToBookingEntity(bookingUpdateDto, bookingEntity);

    BookingStatus currentStatus = bookingEntity.getStatus();
    RoomEntity roomToUpdate = bookingEntity.getRoom();

    if (roomToUpdate != null && currentStatus != null && currentStatus != previousStatus) {
      if (currentStatus == BookingStatus.CONFIRMED && roomToUpdate.getStatus() != RoomStatus.BOOKED) {
        roomToUpdate.setStatus(RoomStatus.BOOKED);
        this.roomRepository.save(roomToUpdate);
      }

      if ((currentStatus == BookingStatus.COMPLETED || currentStatus == BookingStatus.CANCELLED)
          && roomToUpdate.getStatus() != RoomStatus.AVAILABLE) {
        roomToUpdate.setStatus(RoomStatus.AVAILABLE);
        this.roomRepository.save(roomToUpdate);
      }
    }

    bookingEntity = this.bookingRepository.save(bookingEntity);

    if (currentStatus == BookingStatus.CONFIRMED && previousStatus != BookingStatus.CONFIRMED) {
      try {
        this.emailService.sendBookingQrConfirmation(bookingEntity);
      } catch (Exception ex) {
        System.out.println("Failed to send booking QR email: " + ex.getMessage());
      }
    }

    BookingResponseDto bookingResponseDto = this.bookingConverter.toBookingResponseDto(bookingEntity);
    return ResponseDto.success(bookingResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    BookingEntity bookingEntity = this.bookingRepository.findById(id).orElse(null);
    if (bookingEntity == null) {
      return ResponseDto.notFound("Booking not found");
    }

    this.bookingRepository.delete(bookingEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<BookingResponseDto>>> find(BookingFindDto query,
      Pageable pageable) {
    Specification<BookingEntity> bookingSpec = this.bookingRepository.hasCriteria(query);

    Page<BookingEntity> bookingPage = this.bookingRepository.findAll(bookingSpec, pageable);

    int page = bookingPage.getNumber();
    int size = bookingPage.getSize();
    int totalPages = bookingPage.getTotalPages();
    List<BookingEntity> items = bookingPage.getContent();

    List<BookingResponseDto> bookingResponseDtos = items.stream()
        .map(item -> this.bookingConverter.toBookingResponseDto(item)).toList();
    ResponseSpecification<BookingResponseDto> bookingResponseSpecification = ResponseSpecification
        .<BookingResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(bookingResponseDtos)
        .build();
    return ResponseDto.success(bookingResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<BookingResponseDto>> findById(Long id) {
    BookingEntity bookingEntity = this.bookingRepository.findById(id).orElse(null);
    if (bookingEntity == null) {
      return ResponseDto.notFound("Booking not found");
    }

    BookingResponseDto bookingResponseDto = this.bookingConverter.toBookingResponseDto(bookingEntity);
    return ResponseDto.success(bookingResponseDto);
  }
}
