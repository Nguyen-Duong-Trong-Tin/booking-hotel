package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.amenities.AmenityCreateDto;
import com.bookingHotel.dtos.amenities.AmenityResponseDto;
import com.bookingHotel.dtos.amenities.AmenityUpdateDto;
import com.bookingHotel.repositories.entities.AmenityEntity;

@Component
public class AmenityConverter {
  @Autowired
  private ModelMapper modelMapper;

  public AmenityEntity toAmenityEntity(AmenityCreateDto amenityCreateDto) {
    return this.modelMapper.map(amenityCreateDto, AmenityEntity.class);
  }

  public AmenityResponseDto toAmenityResponseDto(AmenityEntity amenityEntity) {
    return this.modelMapper.map(amenityEntity, AmenityResponseDto.class);
  }

  public AmenityEntity copyToAmenityEntity(AmenityUpdateDto amenityUpdateDto, AmenityEntity amenityEntity) {
    this.modelMapper.map(amenityUpdateDto, amenityEntity);
    return amenityEntity;
  }
}
