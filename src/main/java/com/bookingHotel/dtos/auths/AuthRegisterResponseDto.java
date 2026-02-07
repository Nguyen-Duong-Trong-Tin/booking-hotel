package com.bookingHotel.dtos.auths;

import com.bookingHotel.dtos.roles.RoleResponseDto;

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
public class AuthRegisterResponseDto {
  private Long id;
  private String fullName;
  private String email;
  private String phone;
  private RoleResponseDto role;
}
