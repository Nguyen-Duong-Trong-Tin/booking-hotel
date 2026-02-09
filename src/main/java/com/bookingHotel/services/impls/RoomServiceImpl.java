package com.bookingHotel.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.RoomConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.rooms.RoomCreateDto;
import com.bookingHotel.dtos.rooms.RoomFindDto;
import com.bookingHotel.dtos.rooms.RoomResponseDto;
import com.bookingHotel.dtos.rooms.RoomUpdateDto;
import com.bookingHotel.repositories.CategoryRepository;
import com.bookingHotel.repositories.RoomRepository;
import com.bookingHotel.repositories.entities.CategoryEntity;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.services.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
  @Autowired
  private RoomConverter roomConverter;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public ResponseEntity<ResponseDto<RoomResponseDto>> create(RoomCreateDto roomCreateDto) {
    if (this.roomRepository.existsByRoomNumber(roomCreateDto.getRoomNumber())) {
      return ResponseDto.badRequest(List.of("Room number already exists"));
    }

    CategoryEntity categoryEntity = this.categoryRepository.findById(roomCreateDto.getCategoryId()).orElse(null);
    if (categoryEntity == null) {
      return ResponseDto.badRequest(List.of("Category not found"));
    }

    RoomEntity roomEntity = this.roomConverter.toRoomEntity(roomCreateDto);
    roomEntity.setCategory(categoryEntity);

    roomEntity = this.roomRepository.save(roomEntity);

    RoomResponseDto roomResponseDto = this.roomConverter.toRoomResponseDto(roomEntity);

    return ResponseDto.created(roomResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<RoomResponseDto>> update(Long id, RoomUpdateDto roomUpdateDto) {
    RoomEntity roomEntity = this.roomRepository.findById(id).orElse(null);
    if (roomEntity == null) {
      return ResponseDto.notFound("Room not found");
    }

    if (roomUpdateDto.getRoomNumber() != null && !roomUpdateDto.getRoomNumber().equals(roomEntity.getRoomNumber())) {
      if (this.roomRepository.existsByRoomNumber(roomUpdateDto.getRoomNumber())) {
        return ResponseDto.badRequest(List.of("Room number already exists"));
      }
    }

    if (roomUpdateDto.getCategoryId() != null) {
      CategoryEntity categoryEntity = this.categoryRepository.findById(roomUpdateDto.getCategoryId()).orElse(null);
      if (categoryEntity == null) {
        return ResponseDto.badRequest(List.of("Category not found"));
      }
      roomEntity.setCategory(categoryEntity);
    }

    this.roomConverter.copyToRoomEntity(roomUpdateDto, roomEntity);

    roomEntity = this.roomRepository.save(roomEntity);

    RoomResponseDto roomResponseDto = this.roomConverter.toRoomResponseDto(roomEntity);
    return ResponseDto.success(roomResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    RoomEntity roomEntity = this.roomRepository.findById(id).orElse(null);
    if (roomEntity == null) {
      return ResponseDto.notFound("Room not found");
    }

    this.roomRepository.delete(roomEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<RoomResponseDto>>> find(RoomFindDto query,
      Pageable pageable) {
    Specification<RoomEntity> roomSpec = this.roomRepository.hasCriteria(query);

    Page<RoomEntity> roomPage = this.roomRepository.findAll(roomSpec, pageable);

    int page = roomPage.getNumber();
    int size = roomPage.getSize();
    int totalPages = roomPage.getTotalPages();
    List<RoomEntity> items = roomPage.getContent();

    List<RoomResponseDto> roomResponseDtos = items.stream()
        .map(item -> this.roomConverter.toRoomResponseDto(item)).toList();
    ResponseSpecification<RoomResponseDto> roomResponseSpecification = ResponseSpecification
        .<RoomResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(roomResponseDtos)
        .build();
    return ResponseDto.success(roomResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<RoomResponseDto>> findById(Long id) {
    RoomEntity roomEntity = this.roomRepository.findById(id).orElse(null);
    if (roomEntity == null) {
      return ResponseDto.notFound("Room not found");
    }

    RoomResponseDto roomResponseDto = this.roomConverter.toRoomResponseDto(roomEntity);
    return ResponseDto.success(roomResponseDto);
  }
}
