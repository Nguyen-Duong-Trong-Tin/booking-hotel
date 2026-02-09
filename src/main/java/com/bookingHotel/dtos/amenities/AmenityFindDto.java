package com.bookingHotel.dtos.amenities;

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
public class AmenityFindDto {
  private String name;
  private String iconUrl;
}
