package com.bookingHotel.dtos.auths;

import jakarta.validation.constraints.NotBlank;
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
public class AuthLoginDto {
  @NotBlank(message = "Email is not blank")
  private String email;

  @NotBlank(message = "Password is not blank")
  private String password;
}
