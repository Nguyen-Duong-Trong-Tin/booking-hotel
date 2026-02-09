package com.bookingHotel.dtos.rooms;

import java.math.BigDecimal;

import com.bookingHotel.repositories.enums.RoomStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RoomCreateDto {
  @NotBlank(message = "Room number is not blank")
  @Size(max = 20, message = "Room number maximum length is 20 characters")
  private String roomNumber;

  @NotNull(message = "Price is required")
  private BigDecimal price;

  @NotNull(message = "Capacity is required")
  private Integer capacity;

  @NotNull(message = "Status is required")
  private RoomStatus status;

  @NotNull(message = "Category is required")
  private Long categoryId;
}
