package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.bookings.BookingCreateDto;
import com.bookingHotel.dtos.bookings.BookingResponseDto;
import com.bookingHotel.dtos.bookings.BookingUpdateDto;
import com.bookingHotel.repositories.entities.BookingEntity;

@Component
public class BookingConverter {
  @Autowired
  private ModelMapper modelMapper;

  public BookingEntity toBookingEntity(BookingCreateDto bookingCreateDto) {
    return this.modelMapper.map(bookingCreateDto, BookingEntity.class);
  }

  public BookingResponseDto toBookingResponseDto(BookingEntity bookingEntity) {
    return this.modelMapper.map(bookingEntity, BookingResponseDto.class);
  }

  public void copyToBookingEntity(BookingUpdateDto bookingUpdateDto, BookingEntity bookingEntity) {
    if (bookingUpdateDto.getCheckIn() != null) {
      bookingEntity.setCheckIn(bookingUpdateDto.getCheckIn());
    }

    if (bookingUpdateDto.getCheckOut() != null) {
      bookingEntity.setCheckOut(bookingUpdateDto.getCheckOut());
    }

    if (bookingUpdateDto.getTotalPrice() != null) {
      bookingEntity.setTotalPrice(bookingUpdateDto.getTotalPrice());
    }

    if (bookingUpdateDto.getStatus() != null) {
      bookingEntity.setStatus(bookingUpdateDto.getStatus());
    }
  }
}
