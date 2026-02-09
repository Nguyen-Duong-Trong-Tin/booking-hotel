package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.payments.PaymentCreateDto;
import com.bookingHotel.dtos.payments.PaymentResponseDto;
import com.bookingHotel.dtos.payments.PaymentUpdateDto;
import com.bookingHotel.repositories.entities.PaymentEntity;

@Component
public class PaymentConverter {
  @Autowired
  private ModelMapper modelMapper;

  public PaymentEntity toPaymentEntity(PaymentCreateDto paymentCreateDto) {
    return this.modelMapper.map(paymentCreateDto, PaymentEntity.class);
  }

  public PaymentResponseDto toPaymentResponseDto(PaymentEntity paymentEntity) {
    return this.modelMapper.map(paymentEntity, PaymentResponseDto.class);
  }

  public void copyToPaymentEntity(PaymentUpdateDto paymentUpdateDto, PaymentEntity paymentEntity) {
    if (paymentUpdateDto.getPaymentDate() != null) {
      paymentEntity.setPaymentDate(paymentUpdateDto.getPaymentDate());
    }

    if (paymentUpdateDto.getAmount() != null) {
      paymentEntity.setAmount(paymentUpdateDto.getAmount());
    }

    if (paymentUpdateDto.getPaymentMethod() != null) {
      paymentEntity.setPaymentMethod(paymentUpdateDto.getPaymentMethod());
    }

    if (paymentUpdateDto.getStatus() != null) {
      paymentEntity.setStatus(paymentUpdateDto.getStatus());
    }
  }
}
