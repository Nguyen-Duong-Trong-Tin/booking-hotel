package com.bookingHotel.services;

import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ai.AiChatRequestDto;
import com.bookingHotel.dtos.ai.AiChatResponseDto;

public interface AiService {
  ResponseEntity<ResponseDto<AiChatResponseDto>> chatRooms(AiChatRequestDto body);
}
