package com.bookingHotel.dtos.rooms;

import java.math.BigDecimal;

import com.bookingHotel.dtos.categories.CategoryResponseDto;
import com.bookingHotel.repositories.enums.RoomStatus;

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
public class RoomResponseDto {
  private Long id;
  private String roomNumber;
  private BigDecimal price;
  private Integer capacity;
  private RoomStatus status;
  private CategoryResponseDto category;
}
