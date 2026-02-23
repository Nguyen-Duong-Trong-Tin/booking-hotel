package com.bookingHotel.dtos.roomImages;

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
public class RoomImageResponseDto {
  private Long id;
  private String url;
  private Boolean isPresentative;
}
