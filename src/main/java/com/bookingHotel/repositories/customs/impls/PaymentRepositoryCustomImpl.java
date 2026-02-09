package com.bookingHotel.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bookingHotel.dtos.payments.PaymentFindDto;
import com.bookingHotel.repositories.customs.PaymentRepositoryCustom;
import com.bookingHotel.repositories.entities.PaymentEntity;
import com.bookingHotel.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {
  public Specification<PaymentEntity> hasCriteria(PaymentFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (query.getPaymentDate() != null) {
        predicates.add(criteriaBuilder.equal(root.get("paymentDate"), query.getPaymentDate()));
      }

      if (query.getStatus() != null) {
        predicates.add(criteriaBuilder.equal(root.get("status"), query.getStatus()));
      }

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "paymentMethod", query.getPaymentMethod());

      if (query.getBookingId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("booking").get("id"), query.getBookingId()));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
