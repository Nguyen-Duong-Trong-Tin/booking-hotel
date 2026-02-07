package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.users.UserFindDto;
import com.bookingHotel.repositories.entities.UserEntity;

public interface UserRepositoryCustom {
  Specification<UserEntity> hasCriteria(UserFindDto query);
}
