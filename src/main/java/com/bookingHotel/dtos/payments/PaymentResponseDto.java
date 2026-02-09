package com.bookingHotel.dtos.payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bookingHotel.dtos.bookings.BookingResponseDto;
import com.bookingHotel.repositories.enums.PaymentStatus;

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
public class PaymentResponseDto {
  private Long id;
  private LocalDateTime paymentDate;
  private BigDecimal amount;
  private String paymentMethod;
  private PaymentStatus status;
  private BookingResponseDto booking;
}
