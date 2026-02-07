package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.auths.AuthRegisterDto;
import com.bookingHotel.dtos.auths.AuthRegisterResponseDto;
import com.bookingHotel.repositories.entities.UserEntity;

@Component
public class AuthConverter {
  @Autowired
  private ModelMapper modelMapper;

  public UserEntity toUserEntity(AuthRegisterDto authRegisterDto) {
    return this.modelMapper.map(authRegisterDto, UserEntity.class);
  }

  public AuthRegisterResponseDto toAuthRegisterResponseDto(UserEntity userEntity) {
    return this.modelMapper.map(userEntity, AuthRegisterResponseDto.class);
  }
}
