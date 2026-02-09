package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.amenities.AmenityCreateDto;
import com.bookingHotel.dtos.amenities.AmenityFindDto;
import com.bookingHotel.dtos.amenities.AmenityResponseDto;
import com.bookingHotel.dtos.amenities.AmenityUpdateDto;

public interface AmenityService {
  ResponseEntity<ResponseDto<AmenityResponseDto>> create(AmenityCreateDto amenityCreateDto);

  ResponseEntity<ResponseDto<AmenityResponseDto>> update(Long id, AmenityUpdateDto amenityUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<AmenityResponseDto>>> find(AmenityFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<AmenityResponseDto>> findById(Long id);
}
