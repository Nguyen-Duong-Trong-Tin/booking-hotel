package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bookingHotel.dtos.categories.CategoryFindDto;
import com.bookingHotel.repositories.customs.CategoryRepositoryCustom;
import com.bookingHotel.repositories.entities.CategoryEntity;
import com.bookingHotel.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
  public Specification<CategoryEntity> hasCriteria(CategoryFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "description", query.getDescription());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}