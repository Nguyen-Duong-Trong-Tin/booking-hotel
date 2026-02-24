package com.bookingHotel.dtos.roomImages;

import jakarta.validation.constraints.NotNull;
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
public class RoomImageCreateAllDto {
  @NotNull(message = "Room id is required")
  private Long roomId;

  private Integer presentativeIndex;
}
