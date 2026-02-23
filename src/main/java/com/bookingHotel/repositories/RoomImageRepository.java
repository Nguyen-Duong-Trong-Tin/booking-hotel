package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingHotel.repositories.entities.RoomImageEntity;

public interface RoomImageRepository extends JpaRepository<RoomImageEntity, Long> {
  Integer countByRoomId(Long roomId);
}
