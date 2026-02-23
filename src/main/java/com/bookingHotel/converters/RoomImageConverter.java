package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.roomImages.RoomImageCreateAllDto;
import com.bookingHotel.dtos.roomImages.RoomImageResponseDto;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.repositories.entities.RoomImageEntity;

@Component
public class RoomImageConverter {
  @Autowired
  private ModelMapper modelMapper;

  public RoomImageEntity toRoomImageEntity(RoomImageCreateAllDto roomImageCreateAllDto, String url) {
    RoomImageEntity roomImageEntity = RoomImageEntity.builder().url(url)
        .room(RoomEntity.builder().id(roomImageCreateAllDto.getRoomId()).build()).build();

    return roomImageEntity;
  }

  public RoomImageResponseDto toRoomImageResponseDto(RoomImageEntity roomImageEntity) {
    return this.modelMapper.map(roomImageEntity, RoomImageResponseDto.class);
  }
}