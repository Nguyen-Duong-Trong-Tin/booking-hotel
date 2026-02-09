package com.bookingHotel.dtos.rooms;

import java.math.BigDecimal;

import com.bookingHotel.repositories.enums.RoomStatus;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomUpdateDto {
  @Size(max = 20, message = "Room number maximum length is 20 characters")
  private String roomNumber;

  private BigDecimal price;

  private Integer capacity;

  private RoomStatus status;

  private Long categoryId;
}
