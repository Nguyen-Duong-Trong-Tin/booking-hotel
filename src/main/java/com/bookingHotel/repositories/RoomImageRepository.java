package com.bookingHotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingHotel.repositories.entities.RoomImageEntity;

public interface RoomImageRepository extends JpaRepository<RoomImageEntity, Long> {
  Integer countByRoomId(Long roomId);
  List<RoomImageEntity> findByRoomId(Long roomId);
  boolean existsByRoomIdAndIsPresentativeTrue(Long roomId);
  void deleteByRoomId(Long roomId);
}
