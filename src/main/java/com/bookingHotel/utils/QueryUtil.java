package com.bookingHotel.utils;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;

public class QueryUtil {
  public static <T> void addLikePredicate(
      Root<T> root,
      CriteriaBuilder cb,
      List<Predicate> predicates,
      String fieldName,
      String fieldValue) {

    if (!ValidationUtil.isNullOrEmpty(fieldValue)) {
      predicates.add(cb.like(
          cb.lower(root.get(fieldName)),
          "%" + fieldValue.toLowerCase() + "%"));
    }
  }
}
