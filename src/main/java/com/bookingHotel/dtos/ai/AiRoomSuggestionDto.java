package com.bookingHotel.dtos.ai;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class AiRoomSuggestionDto {
  private Long roomId;
  private String roomNumber;
  private BigDecimal price;
  private Integer capacity;
  private String category;
  private String reason;
}
