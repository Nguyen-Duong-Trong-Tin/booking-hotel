package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bookingHotel.dtos.roomAmenities.RoomAmenityFindDto;
import com.bookingHotel.repositories.customs.RoomAmenityRepositoryCustom;
import com.bookingHotel.repositories.entities.RoomAmenityEntity;
import com.bookingHotel.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class RoomAmenityRepositoryCustomImpl implements RoomAmenityRepositoryCustom {
  public Specification<RoomAmenityEntity> hasCriteria(RoomAmenityFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "description", query.getDescription());

      if (query.getRoomId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("room").get("id"), query.getRoomId()));
      }

      if (query.getAmenityId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("amenity").get("id"), query.getAmenityId()));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
