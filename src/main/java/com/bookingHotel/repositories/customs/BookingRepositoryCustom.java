package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.bookings.BookingFindDto;
import com.bookingHotel.repositories.entities.BookingEntity;

public interface BookingRepositoryCustom {
  Specification<BookingEntity> hasCriteria(BookingFindDto query);
}
