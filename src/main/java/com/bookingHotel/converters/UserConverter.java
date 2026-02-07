package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.users.UserCreateDto;
import com.bookingHotel.dtos.users.UserResponseDto;
import com.bookingHotel.dtos.users.UserUpdateDto;
import com.bookingHotel.repositories.entities.UserEntity;

@Component
public class UserConverter {
  @Autowired
  private ModelMapper modelMapper;

  public UserEntity toUserEntity(UserCreateDto userCreateDto) {
    return this.modelMapper.map(userCreateDto, UserEntity.class);
  }

  public UserResponseDto toUserResponseDto(UserEntity userEntity) {
    return this.modelMapper.map(userEntity, UserResponseDto.class);
  }

  public void copyToUserEntity(UserUpdateDto userUpdateDto, UserEntity userEntity) {
    if (userUpdateDto.getFullName() != null) {
      userEntity.setFullName(userUpdateDto.getFullName());
    }

    if (userUpdateDto.getEmail() != null) {
      userEntity.setEmail(userUpdateDto.getEmail());
    }

    if (userUpdateDto.getPhone() != null) {
      userEntity.setPhone(userUpdateDto.getPhone());
    }
  }
}
