package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.bookings.BookingCreateDto;
import com.bookingHotel.dtos.bookings.BookingFindDto;
import com.bookingHotel.dtos.bookings.BookingResponseDto;
import com.bookingHotel.dtos.bookings.BookingUpdateDto;

public interface BookingService {
  ResponseEntity<ResponseDto<BookingResponseDto>> create(BookingCreateDto bookingCreateDto);

  ResponseEntity<ResponseDto<BookingResponseDto>> update(Long id, BookingUpdateDto bookingUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<BookingResponseDto>>> find(BookingFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<BookingResponseDto>> findById(Long id);
}
