package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.payments.PaymentCreateDto;
import com.bookingHotel.dtos.payments.PaymentFindDto;
import com.bookingHotel.dtos.payments.PaymentResponseDto;
import com.bookingHotel.dtos.payments.PaymentUpdateDto;

public interface PaymentService {
  ResponseEntity<ResponseDto<PaymentResponseDto>> create(PaymentCreateDto paymentCreateDto);

  ResponseEntity<ResponseDto<PaymentResponseDto>> update(Long id, PaymentUpdateDto paymentUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<PaymentResponseDto>>> find(PaymentFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<PaymentResponseDto>> findById(Long id);
}
