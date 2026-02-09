package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.payments.PaymentFindDto;
import com.bookingHotel.repositories.entities.PaymentEntity;

public interface PaymentRepositoryCustom {
  Specification<PaymentEntity> hasCriteria(PaymentFindDto query);
}
