package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.annotations.Auth;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.rooms.RoomCreateDto;
import com.bookingHotel.dtos.rooms.RoomFindDto;
import com.bookingHotel.dtos.rooms.RoomResponseDto;
import com.bookingHotel.dtos.rooms.RoomUpdateDto;
import com.bookingHotel.services.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/rooms")
public class RoomController {
  @Autowired
  private RoomService roomService;

  @PostMapping
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<RoomResponseDto>> create(@Valid @RequestBody RoomCreateDto roomCreateDto) {
    return this.roomService.create(roomCreateDto);
  }

  @PatchMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<RoomResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody RoomUpdateDto roomUpdateDto) {
    return this.roomService.update(id, roomUpdateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.roomService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<RoomResponseDto>>> find(RoomFindDto query,
      Pageable pageable) {
    return this.roomService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<RoomResponseDto>> findById(@PathVariable Long id) {
    return this.roomService.findById(id);
  }
}
