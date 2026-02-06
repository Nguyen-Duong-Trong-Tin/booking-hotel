package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.roles.RoleFindDto;
import com.bookingHotel.repositories.entities.RoleEntity;

public interface RoleRepositoryCustom {
  Specification<RoleEntity> hasCriteria(RoleFindDto query);
}
