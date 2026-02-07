package com.bookingHotel.services;

import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.auths.AuthLoginDto;
import com.bookingHotel.dtos.auths.AuthLoginResponseDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenResponseDto;
import com.bookingHotel.dtos.auths.AuthRegisterDto;
import com.bookingHotel.dtos.auths.AuthRegisterResponseDto;

public interface AuthService {
  ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(
      AuthRegisterDto authRegisterDto);

  ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto);

  ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(AuthRefreshTokenDto authRefreshTokenDto);
}