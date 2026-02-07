package com.bookingHotel.services.impls;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.AuthConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.auths.AuthLoginDto;
import com.bookingHotel.dtos.auths.AuthLoginResponseDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenResponseDto;
import com.bookingHotel.dtos.auths.AuthRegisterDto;
import com.bookingHotel.dtos.auths.AuthRegisterResponseDto;
import com.bookingHotel.repositories.RoleRepository;
import com.bookingHotel.repositories.UserRepository;
import com.bookingHotel.repositories.entities.RoleEntity;
import com.bookingHotel.repositories.entities.UserEntity;
import com.bookingHotel.services.AuthService;
import com.bookingHotel.utils.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthConverter authConverter;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(AuthRegisterDto authRegisterDto) {
    Boolean existsByEmail = this.userRepository.existsByEmail(authRegisterDto.getEmail());
    if (existsByEmail) {
      return ResponseDto.badRequest(List.of("Email already exists"));
    }

    Boolean existsByPhone = this.userRepository.existsByPhone(authRegisterDto.getPhone());
    if (existsByPhone) {
      return ResponseDto.badRequest(List.of("Phone already exists"));
    }

    UserEntity userEntity = this.authConverter.toUserEntity(authRegisterDto);

    String encodedPassword = passwordEncoder.encode(authRegisterDto.getPassword());
    userEntity.setPassword(encodedPassword);

    RoleEntity roleEntity = this.roleRepository.findByName("User").orElse(null);
    if (roleEntity == null) {
      return ResponseDto.badRequest(List.of("Role not found"));
    }
    userEntity.setRole(roleEntity);

    userEntity = this.userRepository.save(userEntity);
    AuthRegisterResponseDto authRegisterResponseDto = this.authConverter.toAuthRegisterResponseDto(userEntity);
    return ResponseDto.created(authRegisterResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto) {
    UserEntity userEntity = this.userRepository.findByEmail(authLoginDto.getEmail()).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("Email or password wrong");
    }

    boolean isMatch = passwordEncoder.matches(authLoginDto.getPassword(), userEntity.getPassword());
    if (!isMatch) {
      return ResponseDto.notFound("Phone or password wrong");
    }

    String accessToken = this.jwtUtil.generateTokenAC(userEntity.getEmail(), userEntity.getRole().getName());
    String refreshToken = this.jwtUtil.generateTokenRF(userEntity.getEmail(), userEntity.getRole().getName());

    AuthLoginResponseDto userLoginResponseDto = AuthLoginResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
    return ResponseDto.success(userLoginResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(
      AuthRefreshTokenDto authRefreshTokenDto) {
    String accessToken = authRefreshTokenDto.getAccessToken();
    String refreshToken = authRefreshTokenDto.getRefreshToken();

    Map<String, Object> validationStatusAC = jwtUtil.validateAndExtractAC(accessToken);
    if (validationStatusAC.get("status") == "invalid") {
      return ResponseDto.unauthorized();
    }

    Map<String, Object> validationStatusRF = jwtUtil.validateAndExtractRF(refreshToken);
    if (validationStatusRF.get("status") != "success") {
      return ResponseDto.unauthorized();
    }

    String email = (String) validationStatusRF.get("email");
    String role = (String) validationStatusRF.get("role");

    String newAccessToken = this.jwtUtil.generateTokenAC(email, role);
    return ResponseDto.success(AuthRefreshTokenResponseDto.builder()
        .accessToken(newAccessToken)
        .build());
  }
}
