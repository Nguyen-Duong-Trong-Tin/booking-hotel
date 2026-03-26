package com.bookingHotel.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bookingHotel.repositories.entities.BookingEntity;
import com.bookingHotel.repositories.entities.RoomImageEntity;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.repositories.entities.UserEntity;
import com.bookingHotel.services.EmailService;
import com.bookingHotel.utils.QrCodeUtil;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
  @Autowired
  private JavaMailSender mailSender;

  @Value("${booking.mail.from:${spring.mail.username}}")
  private String fromAddress;

  private static final String HOTEL_PHONE = "0832899717";
  private static final String HOTEL_ADDRESS = "Trung Nhut, Can Tho";

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

    String roomImageUrl = resolveRoomImageUrl(room);

    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
      helper.setFrom(fromAddress);
      helper.setTo(user.getEmail());
      helper.setSubject("Booking Received");
      helper.setText(buildHtmlBody(user, room, bookingEntity, roomImageUrl), true);
      mailSender.send(message);
    } catch (Exception ex) {
      System.out.println("Failed to send booking email: " + ex.getMessage());
    }
  }

  @Override
  public void sendBookingQrConfirmation(BookingEntity bookingEntity) {
    if (bookingEntity == null) {
      return;
    }

    UserEntity user = bookingEntity.getUser();
    RoomEntity room = bookingEntity.getRoom();

    if (user == null || room == null || user.getEmail() == null) {
      return;
    }

    String roomImageUrl = resolveRoomImageUrl(room);
    String qrContent = bookingEntity.getId() != null ? bookingEntity.getId().toString() : "";
    byte[] qrBytes = QrCodeUtil.toPng(qrContent, 320);

    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(fromAddress);
      helper.setTo(user.getEmail());
      helper.setSubject("Booking Confirmed - QR Code");
      helper.setText(buildQrHtmlBody(user, room, bookingEntity, roomImageUrl), true);

      if (qrBytes.length > 0) {
        helper.addAttachment("booking-" + bookingEntity.getId() + ".png",
            new ByteArrayResource(qrBytes), "image/png");
      }

      mailSender.send(message);
    } catch (Exception ex) {
      System.out.println("Failed to send booking QR email: " + ex.getMessage());
    }
  }

  private String resolveRoomImageUrl(RoomEntity room) {
    if (room.getRoomImages() == null || room.getRoomImages().isEmpty()) {
      return null;
    }
    for (RoomImageEntity image : room.getRoomImages()) {
      if (Boolean.TRUE.equals(image.getIsPresentative())) {
        return image.getUrl();
      }
    }
    return room.getRoomImages().get(0).getUrl();
  }

  private String buildHtmlBody(UserEntity user, RoomEntity room, BookingEntity bookingEntity, String roomImageUrl) {
    String fullName = user.getFullName() != null ? user.getFullName() : "Guest";
    String roomNumber = room.getRoomNumber() != null ? room.getRoomNumber() : "N/A";
    String checkIn = bookingEntity.getCheckIn() != null ? bookingEntity.getCheckIn().toString() : "N/A";
    String checkOut = bookingEntity.getCheckOut() != null ? bookingEntity.getCheckOut().toString() : "N/A";
    String totalPrice = bookingEntity.getTotalPrice() != null
        ? bookingEntity.getTotalPrice().toPlainString()
        : "0";
    String status = bookingEntity.getStatus() != null ? bookingEntity.getStatus().name() : "N/A";
    String latitude = room.getLatitude() != null ? room.getLatitude().toString() : "N/A";
    String longitude = room.getLongitude() != null ? room.getLongitude().toString() : "N/A";

    StringBuilder html = new StringBuilder();
    html.append("<div style=\"font-family:Arial,sans-serif;line-height:1.6;color:#0f172a;\">")
        .append("<h2 style=\"margin:0 0 12px;\">Booking Confirmation</h2>")
        .append("<p>Hello ").append(fullName).append(",</p>")
        .append("<p>Your booking is confirmed. Here are the details:</p>")
        .append("<table style=\"border-collapse:collapse;width:100%;max-width:520px;\">")
        .append("<tr><td style=\"padding:6px 0;font-weight:bold;\">Room</td><td style=\"padding:6px 0;\">")
        .append(roomNumber).append("</td></tr>")
        .append("<tr><td style=\"padding:6px 0;font-weight:bold;\">Check-in</td><td style=\"padding:6px 0;\">")
        .append(checkIn).append("</td></tr>")
        .append("<tr><td style=\"padding:6px 0;font-weight:bold;\">Check-out</td><td style=\"padding:6px 0;\">")
        .append(checkOut).append("</td></tr>")
        .append("<tr><td style=\"padding:6px 0;font-weight:bold;\">Total price</td><td style=\"padding:6px 0;\">")
        .append(totalPrice).append("</td></tr>")
        .append("<tr><td style=\"padding:6px 0;font-weight:bold;\">Status</td><td style=\"padding:6px 0;\">")
        .append(status).append("</td></tr>")
        .append("</table>")
        .append("<div style=\"margin-top:16px;\"><strong>Hotel contact</strong></div>")
        .append("<div>Phone: ").append(HOTEL_PHONE).append("</div>")
        .append("<div>Address: ").append(HOTEL_ADDRESS).append("</div>")
        .append("<div>Location: ").append(latitude).append(", ").append(longitude).append("</div>");

    if (roomImageUrl != null && !roomImageUrl.isBlank()) {
      html.append("<div style=\"margin-top:16px;\">")
          .append("<img src=\"")
          .append(roomImageUrl)
          .append("\" alt=\"Room image\" style=\"max-width:520px;width:100%;border-radius:12px;\"/>")
          .append("</div>");
    }

    html.append("<p style=\"margin-top:16px;\">Thank you for choosing Booking Hotel.</p>")
        .append("</div>");

    return html.toString();
  }

  private String buildQrHtmlBody(UserEntity user, RoomEntity room, BookingEntity bookingEntity, String roomImageUrl) {
    String base = buildHtmlBody(user, room, bookingEntity, roomImageUrl);
    return base.replace("</div>",
        "<p style=\"margin-top:12px;\">Please show the attached QR code at check-in.</p></div>");
  }
}
