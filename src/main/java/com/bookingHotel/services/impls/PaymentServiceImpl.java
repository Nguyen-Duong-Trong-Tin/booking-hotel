package com.bookingHotel.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.PaymentConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.payments.PaymentCreateDto;
import com.bookingHotel.dtos.payments.PaymentFindDto;
import com.bookingHotel.dtos.payments.PaymentResponseDto;
import com.bookingHotel.dtos.payments.PaymentUpdateDto;
import com.bookingHotel.repositories.BookingRepository;
import com.bookingHotel.repositories.PaymentRepository;
import com.bookingHotel.repositories.entities.BookingEntity;
import com.bookingHotel.repositories.entities.PaymentEntity;
import com.bookingHotel.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
  @Autowired
  private PaymentConverter paymentConverter;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private BookingRepository bookingRepository;

  @Override
  public ResponseEntity<ResponseDto<PaymentResponseDto>> create(PaymentCreateDto paymentCreateDto) {
    BookingEntity bookingEntity = this.bookingRepository.findById(paymentCreateDto.getBookingId()).orElse(null);
    if (bookingEntity == null) {
      return ResponseDto.badRequest(List.of("Booking not found"));
    }

    if (this.paymentRepository.existsByBooking_Id(paymentCreateDto.getBookingId())) {
      return ResponseDto.badRequest(List.of("Payment already exists for this booking"));
    }

    PaymentEntity paymentEntity = this.paymentConverter.toPaymentEntity(paymentCreateDto);
    paymentEntity.setBooking(bookingEntity);

    paymentEntity = this.paymentRepository.save(paymentEntity);

    PaymentResponseDto paymentResponseDto = this.paymentConverter.toPaymentResponseDto(paymentEntity);

    return ResponseDto.created(paymentResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<PaymentResponseDto>> update(Long id, PaymentUpdateDto paymentUpdateDto) {
    PaymentEntity paymentEntity = this.paymentRepository.findById(id).orElse(null);
    if (paymentEntity == null) {
      return ResponseDto.notFound("Payment not found");
    }

    if (paymentUpdateDto.getBookingId() != null
        && !paymentUpdateDto.getBookingId().equals(paymentEntity.getBooking().getId())) {
      BookingEntity bookingEntity = this.bookingRepository.findById(paymentUpdateDto.getBookingId()).orElse(null);
      if (bookingEntity == null) {
        return ResponseDto.badRequest(List.of("Booking not found"));
      }

      if (this.paymentRepository.existsByBooking_Id(paymentUpdateDto.getBookingId())) {
        return ResponseDto.badRequest(List.of("Payment already exists for this booking"));
      }

      paymentEntity.setBooking(bookingEntity);
    }

    this.paymentConverter.copyToPaymentEntity(paymentUpdateDto, paymentEntity);

    paymentEntity = this.paymentRepository.save(paymentEntity);

    PaymentResponseDto paymentResponseDto = this.paymentConverter.toPaymentResponseDto(paymentEntity);
    return ResponseDto.success(paymentResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    PaymentEntity paymentEntity = this.paymentRepository.findById(id).orElse(null);
    if (paymentEntity == null) {
      return ResponseDto.notFound("Payment not found");
    }

    this.paymentRepository.delete(paymentEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<PaymentResponseDto>>> find(PaymentFindDto query,
      Pageable pageable) {
    Specification<PaymentEntity> paymentSpec = this.paymentRepository.hasCriteria(query);

    Page<PaymentEntity> paymentPage = this.paymentRepository.findAll(paymentSpec, pageable);

    int page = paymentPage.getNumber();
    int size = paymentPage.getSize();
    int totalPages = paymentPage.getTotalPages();
    List<PaymentEntity> items = paymentPage.getContent();

    List<PaymentResponseDto> paymentResponseDtos = items.stream()
        .map(item -> this.paymentConverter.toPaymentResponseDto(item)).toList();
    ResponseSpecification<PaymentResponseDto> paymentResponseSpecification = ResponseSpecification
        .<PaymentResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(paymentResponseDtos)
        .build();
    return ResponseDto.success(paymentResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<PaymentResponseDto>> findById(Long id) {
    PaymentEntity paymentEntity = this.paymentRepository.findById(id).orElse(null);
    if (paymentEntity == null) {
      return ResponseDto.notFound("Payment not found");
    }

    PaymentResponseDto paymentResponseDto = this.paymentConverter.toPaymentResponseDto(paymentEntity);
    return ResponseDto.success(paymentResponseDto);
  }
}
