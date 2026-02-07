package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.users.UserCreateDto;
import com.bookingHotel.dtos.users.UserFindDto;
import com.bookingHotel.dtos.users.UserResponseDto;
import com.bookingHotel.dtos.users.UserUpdateDto;

public interface UserService {
  ResponseEntity<ResponseDto<UserResponseDto>> create(UserCreateDto body);

  ResponseEntity<ResponseDto<UserResponseDto>> update(Long id, UserUpdateDto body);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<UserResponseDto>> findById(Long id);
}
