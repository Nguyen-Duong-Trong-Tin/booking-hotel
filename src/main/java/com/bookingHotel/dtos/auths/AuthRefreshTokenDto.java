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
public class AuthRefreshTokenDto {
  @NotBlank(message = "Refresh token must not be blank")
  private String accessToken;

  @NotBlank(message = "Refresh token must not be blank")
  private String refreshToken;
}
