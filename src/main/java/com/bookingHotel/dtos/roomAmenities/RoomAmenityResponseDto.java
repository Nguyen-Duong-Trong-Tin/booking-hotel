package com.bookingHotel.dtos.roomAmenities;

import com.bookingHotel.dtos.amenities.AmenityResponseDto;
import com.bookingHotel.dtos.rooms.RoomResponseDto;

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
public class RoomAmenityResponseDto {
  private Long id;
  private String description;
  private RoomResponseDto room;
  private AmenityResponseDto amenity;
}
