package com.bookingHotel.services.impls;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bookingHotel.repositories.entities.BookingEntity;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.repositories.entities.UserEntity;
import com.bookingHotel.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
  @Autowired
  private JavaMailSender mailSender;

  @Value("${booking.mail.from:${spring.mail.username}}");
  private String fromAddress;

  @Override
  public void sendBookingConfirmation(BookingEntity bookingEntity) {
    if (bookingEntity == null) {
      return;
    }

    UserEntity user = bookingEntity.getUser();
    RoomEntity room = bookingEntity.getRoom();

    if (user == null || room == null || user.getEmail() == null) {
      return;
    }

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromAddress);
    message.setTo(user.getEmail());
    message.setSubject("Booking Confirmation");
    message.setText(buildBody(user.getFullName(), room.getRoomNumber(),
        bookingEntity.getCheckIn(), bookingEntity.getCheckOut(),
        bookingEntity.getTotalPrice(), bookingEntity.getStatus().name()));

    mailSender.send(message);
  }

  private String buildBody(String fullName, String roomNumber, LocalDate checkIn,
      LocalDate checkOut, BigDecimal totalPrice, String status) {
    StringBuilder builder = new StringBuilder();
    builder.append("Hello ").append(fullName != null ? fullName : "Guest").append(",\n\n");
    builder.append("Your booking is confirmed. Here are the details:\n\n");
    builder.append("Room: ").append(roomNumber != null ? roomNumber : "N/A").append("\n");
    builder.append("Check-in: ").append(checkIn != null ? checkIn : "N/A").append("\n");
    builder.append("Check-out: ").append(checkOut != null ? checkOut : "N/A").append("\n");
    builder.append("Total price: ").append(totalPrice != null ? totalPrice.toPlainString() : "0").append("\n");
    builder.append("Status: ").append(status != null ? status : "N/A").append("\n\n");
    builder.append("Thank you for choosing Booking Hotel.");
    return builder.toString();
  }
}
