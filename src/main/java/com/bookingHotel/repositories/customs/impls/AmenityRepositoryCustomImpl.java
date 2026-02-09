package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bookingHotel.dtos.amenities.AmenityFindDto;
import com.bookingHotel.repositories.customs.AmenityRepositoryCustom;
import com.bookingHotel.repositories.entities.AmenityEntity;
import com.bookingHotel.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class AmenityRepositoryCustomImpl implements AmenityRepositoryCustom {
  public Specification<AmenityEntity> hasCriteria(AmenityFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "iconUrl", query.getIconUrl());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
