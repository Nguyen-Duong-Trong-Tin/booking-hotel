package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.roomAmenities.RoomAmenityFindDto;
import com.bookingHotel.repositories.entities.RoomAmenityEntity;

public interface RoomAmenityRepositoryCustom {
  Specification<RoomAmenityEntity> hasCriteria(RoomAmenityFindDto query);
}
