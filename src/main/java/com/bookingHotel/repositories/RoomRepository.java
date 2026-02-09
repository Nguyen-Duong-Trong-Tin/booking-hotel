package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.RoomRepositoryCustom;
import com.bookingHotel.repositories.entities.RoomEntity;

public interface RoomRepository
    extends JpaRepository<RoomEntity, Long>, JpaSpecificationExecutor<RoomEntity>, RoomRepositoryCustom {
  boolean existsByRoomNumber(String roomNumber);
}
