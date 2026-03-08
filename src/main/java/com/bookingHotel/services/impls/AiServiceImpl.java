package com.bookingHotel.services.impls;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ai.AiChatRequestDto;
import com.bookingHotel.dtos.ai.AiChatResponseDto;
import com.bookingHotel.repositories.RoomRepository;
import com.bookingHotel.repositories.entities.RoomEntity;
import com.bookingHotel.repositories.enums.RoomStatus;
import com.bookingHotel.services.AiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

@Service
public class AiServiceImpl implements AiService {
  private static final int DEFAULT_LIMIT = 50;
  private static final int MAX_LIMIT = 200;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Value("${gemini.api_key:}")
  private String apiKey;

  @Value("${gemini.model:gemini-2.5-flash}")
  private String model;

  @Value("${gemini.min_interval_ms:2000}")
  private long minIntervalMs;

  private final AtomicLong lastRequestAt = new AtomicLong(0L);

  private volatile Client client;

  @Override
  public ResponseEntity<ResponseDto<AiChatResponseDto>> chatRooms(AiChatRequestDto body) {
    long now = System.currentTimeMillis();
    long last = this.lastRequestAt.get();
    long elapsed = now - last;
    if (last > 0 && elapsed < this.minIntervalMs) {
      long waitMs = this.minIntervalMs - elapsed;
      return ResponseDto.tooManyRequests(List.of("Please wait " + waitMs + "ms before retrying"));
    }

    int limit = body.getLimit() == null ? DEFAULT_LIMIT : body.getLimit();
    if (limit < 1 || limit > MAX_LIMIT) {
      return ResponseDto.badRequest(List.of("Limit must be between 1 and " + MAX_LIMIT));
    }

    if (this.apiKey == null || this.apiKey.isBlank()) {
      return ResponseDto.badRequest(List.of("Gemini API key is not configured"));
    }

    List<RoomEntity> rooms = this.roomRepository
        .findByStatus(RoomStatus.AVAILABLE, PageRequest.of(0, limit))
        .getContent();

    if (rooms.isEmpty()) {
      AiChatResponseDto empty = AiChatResponseDto.builder()
          .answer("No available rooms found")
          .suggestions(List.of())
          .build();
      return ResponseDto.success(empty);
    }

    String roomsJson;
    try {
      List<RoomContext> context = rooms.stream()
          .map(room -> new RoomContext(
              room.getId(),
              room.getRoomNumber(),
              room.getPrice(),
              room.getCapacity(),
              room.getCategory() == null ? null : room.getCategory().getName()))
          .toList();
      roomsJson = this.objectMapper.writeValueAsString(context);
    } catch (JsonProcessingException ex) {
      return ResponseDto.badRequest(List.of("Failed to prepare room data"));
    }

    String prompt = buildPrompt(body.getMessage(), roomsJson);
    this.lastRequestAt.set(now);

    String aiText;
    try {
      GenerateContentConfig config = GenerateContentConfig.builder().build();
      GenerateContentResponse response = getClient().models.generateContent(this.model, prompt, config);
      aiText = response.text();
    } catch (Exception ex) {
      return ResponseDto.badRequest(List.of("AI provider error: " + ex.getMessage()));
    }

    AiChatResponseDto parsed = parseAiResponse(aiText);
    if (parsed == null) {
      parsed = AiChatResponseDto.builder()
          .answer(aiText == null ? "" : aiText)
          .suggestions(List.of())
          .build();
    }

    return ResponseDto.success(parsed);
  }

  private Client getClient() {
    if (this.client == null) {
      synchronized (this) {
        if (this.client == null) {
          this.client = Client.builder().apiKey(this.apiKey).build();
        }
      }
    }
    return this.client;
  }

  private String buildPrompt(String message, String roomsJson) {
    return "You are a hotel booking assistant. Use ONLY the provided room list. "
        + "Return JSON with fields: answer (string) and suggestions (array). "
        + "Each suggestion must include: roomId, roomNumber, price, capacity, category, reason. "
        + "If no rooms match, return an empty suggestions array.\n"
        + "User request: " + message + "\n"
        + "Rooms: " + roomsJson;
  }

  private AiChatResponseDto parseAiResponse(String aiText) {
    if (aiText == null || aiText.isBlank()) {
      return null;
    }

    String jsonPayload = extractJson(aiText);
    try {
      return this.objectMapper.readValue(jsonPayload, AiChatResponseDto.class);
    } catch (Exception ex) {
      return null;
    }
  }

  private String extractJson(String aiText) {
    String trimmed = aiText.trim();
    if (trimmed.startsWith("```")) {
      int firstNewline = trimmed.indexOf('\n');
      int lastFence = trimmed.lastIndexOf("```");
      if (firstNewline > 0 && lastFence > firstNewline) {
        return trimmed.substring(firstNewline + 1, lastFence).trim();
      }
    }

    int start = trimmed.indexOf('{');
    int end = trimmed.lastIndexOf('}');
    if (start >= 0 && end > start) {
      return trimmed.substring(start, end + 1);
    }
    return trimmed;
  }

  private record RoomContext(Long roomId, String roomNumber, BigDecimal price, Integer capacity, String category) {
  }
}
