package com.bookingHotel.dtos.bookings;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookingHotel.repositories.enums.BookingStatus;

import jakarta.validation.constraints.NotNull;
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
public class BookingCreateDto {
  @NotNull(message = "Check-in is required")
  private LocalDate checkIn;

  @NotNull(message = "Check-out is required")
  private LocalDate checkOut;

  @NotNull(message = "Total price is required")
  private BigDecimal totalPrice;

  @NotNull(message = "Status is required")
  private BookingStatus status;

  @NotNull(message = "User is required")
  private Long userId;

  @NotNull(message = "Room is required")
  private Long roomId;
}
