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

  // --- LIKE STARTS WITH ---
  public static <T> void addLikeStartsWithPredicate(
          Root<T> root,
          CriteriaBuilder cb,
          List<Predicate> predicates,
          String fieldName,
          String fieldValue) {

      if (!ValidationUtil.isNullOrEmpty(fieldValue)) {
          predicates.add(cb.like(
                  cb.lower(root.get(fieldName)),
                  fieldValue.toLowerCase() + "%"));
      }
  }

  // --- LIKE ENDS WITH ---
  public static <T> void addLikeEndsWithPredicate(
          Root<T> root,
          CriteriaBuilder cb,
          List<Predicate> predicates,
          String fieldName,
          String fieldValue) {

      if (!ValidationUtil.isNullOrEmpty(fieldValue)) {
          predicates.add(cb.like(
                  cb.lower(root.get(fieldName)),
                  "%" + fieldValue.toLowerCase()));
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
          if (fieldValue instanceof String && ValidationUtil.isNullOrEmpty((String) fieldValue)) {
              return;
          }
          predicates.add(cb.equal(root.get(fieldName), fieldValue));
      }
  }

  // --- Case-insensitive String equality ---
  public static <T> void addEqualIgnoreCasePredicate(
          Root<T> root,
          CriteriaBuilder cb,
          List<Predicate> predicates,
          String fieldName,
          String fieldValue) {

      if (!ValidationUtil.isNullOrEmpty(fieldValue)) {
          predicates.add(cb.equal(cb.lower(root.get(fieldName)), fieldValue.toLowerCase()));
      }
  }

  // --- NOT EQUAL ---
  public static <T> void addNotEqualPredicate(
          Root<T> root,
          CriteriaBuilder cb,
          List<Predicate> predicates,
          String fieldName,
          Object fieldValue) {

      if (fieldValue != null) {
          if (fieldValue instanceof String && ValidationUtil.isNullOrEmpty((String) fieldValue)) {
              return;
          }
          predicates.add(cb.notEqual(root.get(fieldName), fieldValue));
      }
  }

  // --- IN (Lists of IDs, Enums, etc.) ---
  public static <T> void addInPredicate(
          Root<T> root,
          List<Predicate> predicates,
          String fieldName,
          Collection<?> fieldValues) {

      if (fieldValues != null && !fieldValues.isEmpty()) {
          predicates.add(root.get(fieldName).in(fieldValues));
      }
  }

  // --- GREATER THAN OR EQUAL TO ---
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

  // --- LESS THAN OR EQUAL TO ---
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

  // --- BETWEEN (range queries) ---
  public static <T, Y extends Comparable<? super Y>> void addBetweenPredicate(
          Root<T> root,
          CriteriaBuilder cb,
          List<Predicate> predicates,
          String fieldName,
          Y startValue,
          Y endValue) {

      if (startValue != null && endValue != null) {
          predicates.add(cb.between(root.get(fieldName), startValue, endValue));
      } else if (startValue != null) {
          predicates.add(cb.greaterThanOrEqualTo(root.get(fieldName), startValue));
      } else if (endValue != null) {
          predicates.add(cb.lessThanOrEqualTo(root.get(fieldName), endValue));
      }
  }

  // --- IS NULL / IS NOT NULL ---
  public static <T> void addIsNullPredicate(
          Root<T> root,
          CriteriaBuilder cb,
          List<Predicate> predicates,
          String fieldName) {

      predicates.add(cb.isNull(root.get(fieldName)));
  }

  public static <T> void addIsNotNullPredicate(
          Root<T> root,
          CriteriaBuilder cb,
          List<Predicate> predicates,
          String fieldName) {

      predicates.add(cb.isNotNull(root.get(fieldName)));
  }

  // --- Combine predicates with AND / OR ---
  public static Predicate combineWithAnd(CriteriaBuilder cb, List<Predicate> predicates) {
      return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
  }

  public static Predicate combineWithOr(CriteriaBuilder cb, List<Predicate> predicates) {
      return predicates.isEmpty() ? null : cb.or(predicates.toArray(new Predicate[0]));
  }
}
