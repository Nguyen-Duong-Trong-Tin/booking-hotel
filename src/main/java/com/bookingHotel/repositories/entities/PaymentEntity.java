package com.bookingHotel.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bookingHotel.repositories.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "paymentDate", nullable = false)
  private LocalDateTime paymentDate;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(name = "paymentMethod", nullable = false, length = 50)
  private String paymentMethod;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PaymentStatus status;

  @OneToOne
  @JoinColumn(name = "bookingId", nullable = false, unique = true)
  private BookingEntity booking;
}
