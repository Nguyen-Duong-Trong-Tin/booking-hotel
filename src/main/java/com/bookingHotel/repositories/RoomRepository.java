package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bookingHotel.repositories.customs.RoomRepositoryCustom;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.repositories.enums.RoomStatus;

public interface RoomRepository
    extends JpaRepository<RoomEntity, Long>, JpaSpecificationExecutor<RoomEntity>, RoomRepositoryCustom {
  boolean existsByRoomNumber(String roomNumber);

  Page<RoomEntity> findByStatus(RoomStatus status, Pageable pageable);
}
