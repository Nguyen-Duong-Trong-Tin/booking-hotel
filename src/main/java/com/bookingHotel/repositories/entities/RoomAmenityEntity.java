package com.bookingHotel.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roomAmenities")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomAmenityEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 255)
  private String description;

  @ManyToOne
  @JoinColumn(name = "roomId", nullable = false)
  private RoomEntity room;

  @ManyToOne
  @JoinColumn(name = "amenityId", nullable = false)
  private AmenityEntity amenity;
}
