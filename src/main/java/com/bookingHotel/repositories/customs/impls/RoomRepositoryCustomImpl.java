package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bookingHotel.dtos.rooms.RoomFindDto;
import com.bookingHotel.repositories.customs.RoomRepositoryCustom;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.repositories.enums.RoomStatus;
import com.bookingHotel.utils.QueryUtil;
import com.bookingHotel.utils.ValidationUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {
  public Specification<RoomEntity> hasCriteria(RoomFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "roomNumber", query.getRoomNumber());

      if (!ValidationUtil.isNullOrEmpty(query.getCategoryName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("category").get("name")),
            "%" + query.getCategoryName().toLowerCase() + "%"));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getStatus())) {
        try {
          RoomStatus status = RoomStatus.valueOf(query.getStatus().toUpperCase());
          predicates.add(criteriaBuilder.equal(root.get("status"), status));
        } catch (IllegalArgumentException ex) {
          // Ignore invalid status filter values.
        }
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
