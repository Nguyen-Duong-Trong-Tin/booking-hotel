package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.annotations.Auth;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.auths.AuthLoginDto;
import com.bookingHotel.dtos.auths.AuthLoginResponseDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenResponseDto;
import com.bookingHotel.dtos.auths.AuthRegisterDto;
import com.bookingHotel.dtos.auths.AuthRegisterResponseDto;
import com.bookingHotel.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(
      @Valid @RequestBody AuthRegisterDto authRegisterDto) {
    return this.authService.register(authRegisterDto);
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(
      @Valid @RequestBody AuthLoginDto authLoginDto) {
    return this.authService.login(authLoginDto);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(
      @RequestBody @Valid AuthRefreshTokenDto authRefreshTokenDto) {
    return this.authService.refreshToken(authRefreshTokenDto);
  }
}
