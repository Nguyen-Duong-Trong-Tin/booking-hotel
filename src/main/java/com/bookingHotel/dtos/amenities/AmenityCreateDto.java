package com.bookingHotel.dtos.amenities;

import jakarta.validation.constraints.NotBlank;
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
public class AmenityCreateDto {
  @NotBlank(message = "Name is not blank")
  @Size(max = 100, message = "Name maximum length is 100 characters")
  private String name;

  @Size(max = 255, message = "Icon url maximum length is 255 characters")
  private String iconUrl;
}
