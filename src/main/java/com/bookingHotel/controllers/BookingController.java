package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.annotations.Auth;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.bookings.BookingCreateDto;
import com.bookingHotel.dtos.bookings.BookingFindDto;
import com.bookingHotel.dtos.bookings.BookingResponseDto;
import com.bookingHotel.dtos.bookings.BookingUpdateDto;
import com.bookingHotel.services.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {
  @Autowired
  private BookingService bookingService;

  @PostMapping
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<BookingResponseDto>> create(@Valid @RequestBody BookingCreateDto bookingCreateDto) {
    return this.bookingService.create(bookingCreateDto);
  }

  @PatchMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<BookingResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody BookingUpdateDto bookingUpdateDto) {
    return this.bookingService.update(id, bookingUpdateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.bookingService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<BookingResponseDto>>> find(BookingFindDto query,
      Pageable pageable) {
    return this.bookingService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<BookingResponseDto>> findById(@PathVariable Long id) {
    return this.bookingService.findById(id);
  }
}
