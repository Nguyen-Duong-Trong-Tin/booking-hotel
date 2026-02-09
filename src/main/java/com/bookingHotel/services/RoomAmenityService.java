package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityCreateDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityFindDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityResponseDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityUpdateDto;

public interface RoomAmenityService {
  ResponseEntity<ResponseDto<RoomAmenityResponseDto>> create(RoomAmenityCreateDto roomAmenityCreateDto);

  ResponseEntity<ResponseDto<RoomAmenityResponseDto>> update(Long id, RoomAmenityUpdateDto roomAmenityUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<RoomAmenityResponseDto>>> find(RoomAmenityFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<RoomAmenityResponseDto>> findById(Long id);
}
