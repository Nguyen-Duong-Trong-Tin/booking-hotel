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
import com.bookingHotel.dtos.roomAmenities.RoomAmenityCreateDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityFindDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityResponseDto;
import com.bookingHotel.dtos.roomAmenities.RoomAmenityUpdateDto;
import com.bookingHotel.services.RoomAmenityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/room-amenities")
public class RoomAmenityController {
  @Autowired
  private RoomAmenityService roomAmenityService;

  @PostMapping
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<RoomAmenityResponseDto>> create(
      @Valid @RequestBody RoomAmenityCreateDto roomAmenityCreateDto) {
    return this.roomAmenityService.create(roomAmenityCreateDto);
  }

  @PatchMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<RoomAmenityResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody RoomAmenityUpdateDto roomAmenityUpdateDto) {
    return this.roomAmenityService.update(id, roomAmenityUpdateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.roomAmenityService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<RoomAmenityResponseDto>>> find(RoomAmenityFindDto query,
      Pageable pageable) {
    return this.roomAmenityService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<RoomAmenityResponseDto>> findById(@PathVariable Long id) {
    return this.roomAmenityService.findById(id);
  }
}
