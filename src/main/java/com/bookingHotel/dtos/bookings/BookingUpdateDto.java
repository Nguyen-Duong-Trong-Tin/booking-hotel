package com.bookingHotel.dtos.bookings;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class BookingUpdateDto {
  private LocalDate checkIn;

  private LocalDate checkOut;

  private BigDecimal totalPrice;

  private BookingStatus status;

  private Long userId;

  private Long roomId;
}
