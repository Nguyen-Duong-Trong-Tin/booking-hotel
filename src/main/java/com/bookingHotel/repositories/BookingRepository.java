package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.BookingRepositoryCustom;
import com.bookingHotel.repositories.entities.BookingEntity;

public interface BookingRepository
    extends JpaRepository<BookingEntity, Long>, JpaSpecificationExecutor<BookingEntity>, BookingRepositoryCustom {

}
