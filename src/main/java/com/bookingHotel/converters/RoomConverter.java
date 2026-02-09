package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.rooms.RoomCreateDto;
import com.bookingHotel.dtos.rooms.RoomResponseDto;
import com.bookingHotel.dtos.rooms.RoomUpdateDto;
import com.bookingHotel.repositories.entities.RoomEntity;

@Component
public class RoomConverter {
  @Autowired
  private ModelMapper modelMapper;

  public RoomEntity toRoomEntity(RoomCreateDto roomCreateDto) {
    return this.modelMapper.map(roomCreateDto, RoomEntity.class);
  }

  public RoomResponseDto toRoomResponseDto(RoomEntity roomEntity) {
    return this.modelMapper.map(roomEntity, RoomResponseDto.class);
  }

  public void copyToRoomEntity(RoomUpdateDto roomUpdateDto, RoomEntity roomEntity) {
    if (roomUpdateDto.getRoomNumber() != null) {
      roomEntity.setRoomNumber(roomUpdateDto.getRoomNumber());
    }

    if (roomUpdateDto.getPrice() != null) {
      roomEntity.setPrice(roomUpdateDto.getPrice());
    }

    if (roomUpdateDto.getCapacity() != null) {
      roomEntity.setCapacity(roomUpdateDto.getCapacity());
    }

    if (roomUpdateDto.getStatus() != null) {
      roomEntity.setStatus(roomUpdateDto.getStatus());
    }
  }
}
