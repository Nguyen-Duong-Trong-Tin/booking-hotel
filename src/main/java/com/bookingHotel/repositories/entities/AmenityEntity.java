package com.bookingHotel.repositories.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "amenities")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmenityEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(name = "iconUrl")
  private String iconUrl;

  @OneToMany(mappedBy = "amenity")
  private List<RoomAmenityEntity> roomAmenities;
}
