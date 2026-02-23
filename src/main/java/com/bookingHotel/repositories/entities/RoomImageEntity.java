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
@Table(name = "roomImages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomImageEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "url", nullable = false, length = 255)
  private String url;

  @Column(name = "isPresentative")
  @Builder.Default
  private Boolean isPresentative = false;

  @ManyToOne
  @JoinColumn(name = "roomId", nullable = false)
  private RoomEntity room;
}
