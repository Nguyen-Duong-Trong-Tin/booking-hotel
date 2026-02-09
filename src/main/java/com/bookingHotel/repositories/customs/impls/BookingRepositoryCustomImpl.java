package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bookingHotel.dtos.bookings.BookingFindDto;
import com.bookingHotel.repositories.customs.BookingRepositoryCustom;
import com.bookingHotel.repositories.entities.BookingEntity;

import jakarta.persistence.criteria.Predicate;

@Repository
public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {
  public Specification<BookingEntity> hasCriteria(BookingFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (query.getCheckIn() != null) {
        predicates.add(criteriaBuilder.equal(root.get("checkIn"), query.getCheckIn()));
      }

      if (query.getCheckOut() != null) {
        predicates.add(criteriaBuilder.equal(root.get("checkOut"), query.getCheckOut()));
      }

      if (query.getStatus() != null) {
        predicates.add(criteriaBuilder.equal(root.get("status"), query.getStatus()));
      }

      if (query.getUserId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("user").get("id"), query.getUserId()));
      }

      if (query.getRoomId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("room").get("id"), query.getRoomId()));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
