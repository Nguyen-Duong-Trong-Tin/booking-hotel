package com.bookingHotel.dtos.auths;

import com.bookingHotel.dtos.users.UserResponseDto;

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
public class AuthGoogleResponseDto {
  private String accessToken;
  private String refreshToken;
  private Boolean needsProfile;
  private UserResponseDto user;
}
