package com.bookingHotel.dtos.rooms;

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
public class RoomFindDto {
  private String roomNumber;
  private String status;
  private String categoryName;
}
