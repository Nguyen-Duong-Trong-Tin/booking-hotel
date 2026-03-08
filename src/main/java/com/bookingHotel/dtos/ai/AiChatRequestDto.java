package com.bookingHotel.dtos.ai;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class AiChatRequestDto {
  @NotBlank(message = "Message is required")
  private String message;

  @Min(value = 1, message = "Limit must be at least 1")
  @Max(value = 200, message = "Limit must be at most 200")
  private Integer limit;
}
