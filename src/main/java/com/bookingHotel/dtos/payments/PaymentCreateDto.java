package com.bookingHotel.dtos.payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bookingHotel.repositories.enums.PaymentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PaymentCreateDto {
  @NotNull(message = "Payment date is required")
  private LocalDateTime paymentDate;

  @NotNull(message = "Amount is required")
  private BigDecimal amount;

  @NotBlank(message = "Payment method is not blank")
  @Size(max = 50, message = "Payment method maximum length is 50 characters")
  private String paymentMethod;

  @NotNull(message = "Status is required")
  private PaymentStatus status;

  @NotNull(message = "Booking is required")
  private Long bookingId;
}
