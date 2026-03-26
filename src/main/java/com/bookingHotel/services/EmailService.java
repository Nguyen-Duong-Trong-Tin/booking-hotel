package com.bookingHotel.services;

import com.bookingHotel.repositories.entities.BookingEntity;

public interface EmailService {
  void sendBookingConfirmation(BookingEntity bookingEntity);

  void sendBookingQrConfirmation(BookingEntity bookingEntity);
}
