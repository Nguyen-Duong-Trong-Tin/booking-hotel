package com.bookingHotel.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.RoomAmenityConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityCreateDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityFindDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityResponseDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityUpdateDto;
import com.bookingHotel.repositories.AmenityRepository;
import com.bookingHotel.repositories.RoomAmenityRepository;
import com.bookingHotel.repositories.RoomRepository;
import com.bookingHotel.repositories.entities.AmenityEntity;
import com.bookingHotel.repositories.entities.RoomAmenityEntity;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.services.RoomAmenityService;

@Service
public class RoomAmenityServiceImpl implements RoomAmenityService {
  @Autowired
  private RoomAmenityConverter roomAmenityConverter;

  @Autowired
  private RoomAmenityRepository roomAmenityRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private AmenityRepository amenityRepository;

  @Override
  public ResponseEntity<ResponseDto<RoomAmenityResponseDto>> create(RoomAmenityCreateDto roomAmenityCreateDto) {
    RoomEntity roomEntity = this.roomRepository.findById(roomAmenityCreateDto.getRoomId()).orElse(null);
    if (roomEntity == null) {
      return ResponseDto.badRequest(List.of("Room not found"));
    }

    AmenityEntity amenityEntity = this.amenityRepository.findById(roomAmenityCreateDto.getAmenityId()).orElse(null);
    if (amenityEntity == null) {
      return ResponseDto.badRequest(List.of("Amenity not found"));
    }

    RoomAmenityEntity roomAmenityEntity = this.roomAmenityConverter.toRoomAmenityEntity(roomAmenityCreateDto);
    roomAmenityEntity.setRoom(roomEntity);
    roomAmenityEntity.setAmenity(amenityEntity);

    roomAmenityEntity = this.roomAmenityRepository.save(roomAmenityEntity);

    RoomAmenityResponseDto roomAmenityResponseDto = this.roomAmenityConverter
        .toRoomAmenityResponseDto(roomAmenityEntity);

    return ResponseDto.created(roomAmenityResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<RoomAmenityResponseDto>> update(Long id, RoomAmenityUpdateDto roomAmenityUpdateDto) {
    RoomAmenityEntity roomAmenityEntity = this.roomAmenityRepository.findById(id).orElse(null);
    if (roomAmenityEntity == null) {
      return ResponseDto.notFound("Room amenity not found");
    }

    if (roomAmenityUpdateDto.getRoomId() != null) {
      RoomEntity roomEntity = this.roomRepository.findById(roomAmenityUpdateDto.getRoomId()).orElse(null);
      if (roomEntity == null) {
        return ResponseDto.badRequest(List.of("Room not found"));
      }
      roomAmenityEntity.setRoom(roomEntity);
    }

    if (roomAmenityUpdateDto.getAmenityId() != null) {
      AmenityEntity amenityEntity = this.amenityRepository.findById(roomAmenityUpdateDto.getAmenityId()).orElse(null);
      if (amenityEntity == null) {
        return ResponseDto.badRequest(List.of("Amenity not found"));
      }
      roomAmenityEntity.setAmenity(amenityEntity);
    }

    this.roomAmenityConverter.copyToRoomAmenityEntity(roomAmenityUpdateDto, roomAmenityEntity);

    roomAmenityEntity = this.roomAmenityRepository.save(roomAmenityEntity);

    RoomAmenityResponseDto roomAmenityResponseDto = this.roomAmenityConverter
        .toRoomAmenityResponseDto(roomAmenityEntity);
    return ResponseDto.success(roomAmenityResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    RoomAmenityEntity roomAmenityEntity = this.roomAmenityRepository.findById(id).orElse(null);
    if (roomAmenityEntity == null) {
      return ResponseDto.notFound("Room amenity not found");
    }

    this.roomAmenityRepository.delete(roomAmenityEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<RoomAmenityResponseDto>>> find(RoomAmenityFindDto query,
      Pageable pageable) {
    Specification<RoomAmenityEntity> roomAmenitySpec = this.roomAmenityRepository.hasCriteria(query);

    Page<RoomAmenityEntity> roomAmenityPage = this.roomAmenityRepository.findAll(roomAmenitySpec, pageable);

    int page = roomAmenityPage.getNumber();
    int size = roomAmenityPage.getSize();
    int totalPages = roomAmenityPage.getTotalPages();
    List<RoomAmenityEntity> items = roomAmenityPage.getContent();

    List<RoomAmenityResponseDto> roomAmenityResponseDtos = items.stream()
        .map(item -> this.roomAmenityConverter.toRoomAmenityResponseDto(item)).toList();
    ResponseSpecification<RoomAmenityResponseDto> roomAmenityResponseSpecification = ResponseSpecification
        .<RoomAmenityResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(roomAmenityResponseDtos)
        .build();
    return ResponseDto.success(roomAmenityResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<RoomAmenityResponseDto>> findById(Long id) {
    RoomAmenityEntity roomAmenityEntity = this.roomAmenityRepository.findById(id).orElse(null);
    if (roomAmenityEntity == null) {
      return ResponseDto.notFound("Room amenity not found");
    }

    RoomAmenityResponseDto roomAmenityResponseDto = this.roomAmenityConverter
        .toRoomAmenityResponseDto(roomAmenityEntity);
    return ResponseDto.success(roomAmenityResponseDto);
  }
}
