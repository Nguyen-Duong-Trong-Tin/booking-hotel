package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.AmenityRepositoryCustom;
import com.bookingHotel.repositories.entities.AmenityEntity;

public interface AmenityRepository
    extends JpaRepository<AmenityEntity, Long>, JpaSpecificationExecutor<AmenityEntity>, AmenityRepositoryCustom {

}
