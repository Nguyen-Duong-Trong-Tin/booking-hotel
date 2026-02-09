package com.bookingHotel.dtos.roomAmenities;

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
public class RoomAmenityCreateDto {
  @Size(max = 255, message = "Description maximum length is 255 characters")
  private String description;

  @NotNull(message = "Room is required")
  private Long roomId;

  @NotNull(message = "Amenity is required")
  private Long amenityId;
}
