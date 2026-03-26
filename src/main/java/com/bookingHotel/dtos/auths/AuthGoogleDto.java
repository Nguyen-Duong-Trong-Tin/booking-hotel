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
public class AuthGoogleDto {
  @NotBlank(message = "Google id token is required")
  private String idToken;
}
