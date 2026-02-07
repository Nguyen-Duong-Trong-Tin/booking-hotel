package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bookingHotel.dtos.users.UserFindDto;
import com.bookingHotel.repositories.customs.UserRepositoryCustom;
import com.bookingHotel.repositories.entities.UserEntity;
import com.bookingHotel.utils.QueryUtil;
import com.bookingHotel.utils.ValidationUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
  public Specification<UserEntity> hasCriteria(UserFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "fullName", query.getFullName());
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "email", query.getEmail());
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "phone", query.getPhone());

      if (!ValidationUtil.isNullOrEmpty(query.getRoleName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("role").get("name")),
            "%" + query.getRoleName().toLowerCase() + "%"));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
