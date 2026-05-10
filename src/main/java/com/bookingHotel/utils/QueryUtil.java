package com.bookingHotel.utils;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;

public class QueryUtil {
  // --- LIKE (for partial string matching) ---
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

  // --- EQUAL (Boolean, Integer, Long, Enum, exact String) ---
  public static <T> void addEqualPredicate(
      Root<T> root,
      CriteriaBuilder cb,
      List<Predicate> predicates,
      String fieldName,
      Object fieldValue) {

    if (fieldValue != null) {
      if (fieldValue instanceof String) {
        if (ValidationUtil.isNullOrEmpty((String) fieldValue)) {
          return;
        }
      }

      predicates.add(cb.equal(root.get(fieldName), fieldValue));
    }
  }

  // --- IN (Lists of IDs, Enums, etc.) ---
  public static <T> void addInPredicate(
      Root<T> root,
      List<Predicate> predicates,
      String fieldName,
      List<?> fieldValues) {

    if (fieldValues != null && !fieldValues.isEmpty()) {
      predicates.add(root.get(fieldName).in(fieldValues));
    }
  }

  // --- GREATER THAN OR EQUAL TO (Numbers, Dates) ---
  public static <T, Y extends Comparable<? super Y>> void addGreaterThanOrEqualToPredicate(
      Root<T> root,
      CriteriaBuilder cb,
      List<Predicate> predicates,
      String fieldName,
      Y fieldValue) {

    if (fieldValue != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(fieldName), fieldValue));
    }
  }

  // --- LESS THAN OR EQUAL TO (Numbers, Dates) ---
  public static <T, Y extends Comparable<? super Y>> void addLessThanOrEqualToPredicate(
      Root<T> root,
      CriteriaBuilder cb,
      List<Predicate> predicates,
      String fieldName,
      Y fieldValue) {

    if (fieldValue != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(fieldName), fieldValue));
    }
  }
}
