package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.annotations.Auth;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.payments.PaymentCreateDto;
import com.bookingHotel.dtos.payments.PaymentFindDto;
import com.bookingHotel.dtos.payments.PaymentResponseDto;
import com.bookingHotel.dtos.payments.PaymentUpdateDto;
import com.bookingHotel.services.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {
  @Autowired
  private PaymentService paymentService;

  @PostMapping
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<PaymentResponseDto>> create(@Valid @RequestBody PaymentCreateDto paymentCreateDto) {
    return this.paymentService.create(paymentCreateDto);
  }

  @PatchMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<PaymentResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody PaymentUpdateDto paymentUpdateDto) {
    return this.paymentService.update(id, paymentUpdateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.paymentService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<PaymentResponseDto>>> find(PaymentFindDto query,
      Pageable pageable) {
    return this.paymentService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<PaymentResponseDto>> findById(@PathVariable Long id) {
    return this.paymentService.findById(id);
  }
}
