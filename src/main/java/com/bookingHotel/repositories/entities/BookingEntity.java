package com.bookingHotel.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bookingHotel.repositories.enums.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "checkIn", nullable = false)
  private LocalDate checkIn;

  @Column(name = "checkOut", nullable = false)
  private LocalDate checkOut;

  @Column(name = "totalPrice", nullable = false)
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private BookingStatus status;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "roomId", nullable = false)
  private RoomEntity room;

  @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
  private PaymentEntity payment;
}
