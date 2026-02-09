package com.bookingHotel.dtos.payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bookingHotel.repositories.enums.PaymentStatus;

import jakarta.validation.constraints.Size;
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
public class PaymentUpdateDto {
  private LocalDateTime paymentDate;

  private BigDecimal amount;

  @Size(max = 50, message = "Payment method maximum length is 50 characters")
  private String paymentMethod;

  private PaymentStatus status;

  private Long bookingId;
}
