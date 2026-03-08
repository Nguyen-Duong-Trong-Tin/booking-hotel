package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ai.AiChatRequestDto;
import com.bookingHotel.dtos.ai.AiChatResponseDto;
import com.bookingHotel.services.AiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/ai")
public class AIController {
	@Autowired
	private AiService aiService;

	@PostMapping("/rooms/chat")
	public ResponseEntity<ResponseDto<AiChatResponseDto>> chatRooms(@Valid @RequestBody AiChatRequestDto body) {
		return this.aiService.chatRooms(body);
	}
}
