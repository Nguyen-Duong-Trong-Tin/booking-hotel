package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.roles.RoleFindDto;
import com.bookingHotel.repositories.customs.RoleRepositoryCustom;
import com.bookingHotel.repositories.entities.RoleEntity;
import com.bookingHotel.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {
  public Specification<RoleEntity> hasCriteria(RoleFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "description", query.getDescription());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
