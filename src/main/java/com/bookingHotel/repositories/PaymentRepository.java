package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.PaymentRepositoryCustom;
import com.bookingHotel.repositories.entities.PaymentEntity;

public interface PaymentRepository
    extends JpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity>, PaymentRepositoryCustom {
  boolean existsByBooking_Id(Long bookingId);
}
