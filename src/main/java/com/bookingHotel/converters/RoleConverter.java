package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.roles.RoleCreateDto;
import com.bookingHotel.dtos.roles.RoleResponseDto;
import com.bookingHotel.dtos.roles.RoleUpdateDto;
import com.bookingHotel.repositories.entities.RoleEntity;

@Component
public class RoleConverter {
  @Autowired
  private ModelMapper modelMapper;

  public RoleEntity toRoleEntity(RoleCreateDto roleCreateDto) {
    return this.modelMapper.map(roleCreateDto, RoleEntity.class);
  }

  public RoleResponseDto toRoleResponseDto(RoleEntity roleEntity) {
    return this.modelMapper.map(roleEntity, RoleResponseDto.class);
  }

  public RoleEntity copyToRoleEntity(RoleUpdateDto roleUpdateDto, RoleEntity roleEntity) {
    this.modelMapper.map(roleUpdateDto, roleEntity);
    return roleEntity;
  }
}
