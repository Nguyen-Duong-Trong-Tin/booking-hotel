package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.amenities.AmenityFindDto;
import com.bookingHotel.repositories.entities.AmenityEntity;

public interface AmenityRepositoryCustom {
  Specification<AmenityEntity> hasCriteria(AmenityFindDto query);
}
