package com.bookingHotel.repositories.entities;

import java.math.BigDecimal;
import java.util.List;

import com.bookingHotel.repositories.enums.RoomStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "roomNumber", nullable = false, unique = true, length = 20)
  private String roomNumber;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private Integer capacity;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private RoomStatus status;

  @ManyToOne
  @JoinColumn(name = "categoryId", nullable = false)
  private CategoryEntity category;

  @OneToMany(mappedBy = "room")
  private List<RoomAmenityEntity> roomAmenities;

  @OneToMany(mappedBy = "room")
  private List<BookingEntity> bookings;
}
