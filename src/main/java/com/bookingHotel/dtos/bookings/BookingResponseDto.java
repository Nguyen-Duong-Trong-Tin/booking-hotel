package com.bookingHotel.dtos.bookings;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookingHotel.dtos.rooms.RoomResponseDto;
import com.bookingHotel.dtos.users.UserResponseDto;
import com.bookingHotel.repositories.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingResponseDto {
  private Long id;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private BigDecimal totalPrice;
  private BookingStatus status;
  private UserResponseDto user;
  private RoomResponseDto room;
}
