package com.bookingHotel.dtos.auths;

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
public class AuthRegisterDto {
  @NotBlank(message = "Full name is not blank")
  @Size(max = 100, message = "Full name must be less than 100 characters")
  private String fullName;  

  @NotBlank(message = "Email is not blank")
  @Size(max = 100, message = "Email must be less than 100 characters")
  private String email;

  @NotBlank(message = "Phone is not blank")
  @Size(max = 15, message = "Phone must be less than 15 characters")
  private String phone;

  @NotBlank(message = "Password is not blank")
  @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
  private String password;
}
