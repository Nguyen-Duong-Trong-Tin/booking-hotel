package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.rooms.RoomCreateDto;
import com.bookingHotel.dtos.rooms.RoomFindDto;
import com.bookingHotel.dtos.rooms.RoomResponseDto;
import com.bookingHotel.dtos.rooms.RoomUpdateDto;

public interface RoomService {
  ResponseEntity<ResponseDto<RoomResponseDto>> create(RoomCreateDto roomCreateDto);

  ResponseEntity<ResponseDto<RoomResponseDto>> update(Long id, RoomUpdateDto roomUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<RoomResponseDto>>> find(RoomFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<RoomResponseDto>> findById(Long id);
}
