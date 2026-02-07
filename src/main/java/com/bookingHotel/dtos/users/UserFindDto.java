package com.bookingHotel.dtos.users;

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
public class UserFindDto {
  private String fullName;
  private String email;
  private String phone;
  private String roleName;
}
