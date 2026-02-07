package com.bookingHotel.dtos.auths;

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
public class AuthLoginResponseDto {
  private String accessToken;
  private String refreshToken;
}
