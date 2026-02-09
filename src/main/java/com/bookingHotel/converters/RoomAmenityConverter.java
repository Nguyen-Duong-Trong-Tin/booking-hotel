package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.roomAmenities.RoomAmenityCreateDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityResponseDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityUpdateDto;
import com.bookingHotel.repositories.entities.RoomAmenityEntity;

@Component
public class RoomAmenityConverter {
  @Autowired
  private ModelMapper modelMapper;

  public RoomAmenityEntity toRoomAmenityEntity(RoomAmenityCreateDto roomAmenityCreateDto) {
    return this.modelMapper.map(roomAmenityCreateDto, RoomAmenityEntity.class);
  }

  public RoomAmenityResponseDto toRoomAmenityResponseDto(RoomAmenityEntity roomAmenityEntity) {
    return this.modelMapper.map(roomAmenityEntity, RoomAmenityResponseDto.class);
  }

  public void copyToRoomAmenityEntity(RoomAmenityUpdateDto roomAmenityUpdateDto,
      RoomAmenityEntity roomAmenityEntity) {
    if (roomAmenityUpdateDto.getDescription() != null) {
      roomAmenityEntity.setDescription(roomAmenityUpdateDto.getDescription());
    }
  }
}
