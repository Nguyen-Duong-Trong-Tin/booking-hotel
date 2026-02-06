package com.bookingHotel.dtos.roles;

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
public class RoleUpdateDto {
  @NotBlank(message = "Name is not blank")
  @Size(max = 30, message = "Name maximum length is 30 characters")
  private String name;

  @Size(max = 255, message = "Description maximum length is 255 characters")
  private String description;
}
