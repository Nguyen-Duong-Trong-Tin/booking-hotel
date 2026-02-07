package com.bookingHotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.UserRepositoryCustom;
import com.bookingHotel.repositories.entities.UserEntity;

public interface UserRepository
  extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>, UserRepositoryCustom {
  Boolean existsByEmail(String email);

  Boolean existsByPhone(String phone);

  Optional<UserEntity> findByEmail(String email);
}
