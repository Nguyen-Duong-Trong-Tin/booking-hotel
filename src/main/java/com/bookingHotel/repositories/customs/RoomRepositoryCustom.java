package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.rooms.RoomFindDto;
import com.bookingHotel.repositories.entities.RoomEntity;

public interface RoomRepositoryCustom {
  Specification<RoomEntity> hasCriteria(RoomFindDto query);
}
