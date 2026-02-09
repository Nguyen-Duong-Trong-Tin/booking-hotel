package com.bookingHotel.dtos.payments;

import java.time.LocalDateTime;

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
public class PaymentFindDto {
  private LocalDateTime paymentDate;
  private PaymentStatus status;
  private String paymentMethod;
  private Long bookingId;
}
