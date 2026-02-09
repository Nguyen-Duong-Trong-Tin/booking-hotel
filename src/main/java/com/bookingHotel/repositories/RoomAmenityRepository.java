package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.RoomAmenityRepositoryCustom;
import com.bookingHotel.repositories.entities.RoomAmenityEntity;

public interface RoomAmenityRepository
    extends JpaRepository<RoomAmenityEntity, Long>, JpaSpecificationExecutor<RoomAmenityEntity>,
    RoomAmenityRepositoryCustom {

}
