package com.bookingHotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.RoleRepositoryCustom;
import com.bookingHotel.repositories.entities.RoleEntity;

public interface RoleRepository
                extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity>, RoleRepositoryCustom {

        Optional<RoleEntity> findByName(String name);
}
