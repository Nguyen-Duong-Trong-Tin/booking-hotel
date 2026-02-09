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
import com.bookingHotel.dtos.amenities.AmenityCreateDto;
import com.bookingHotel.dtos.amenities.AmenityFindDto;
import com.bookingHotel.dtos.amenities.AmenityResponseDto;
import com.bookingHotel.dtos.amenities.AmenityUpdateDto;
import com.bookingHotel.services.AmenityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/amenities")
public class AmenityController {
  @Autowired
  private AmenityService amenityService;

  @PostMapping
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<AmenityResponseDto>> create(
      @Valid @RequestBody AmenityCreateDto amenityCreateDto) {
    return this.amenityService.create(amenityCreateDto);
  }

  @PatchMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<AmenityResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody AmenityUpdateDto amenityUpdateDto) {
    return this.amenityService.update(id, amenityUpdateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.amenityService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<AmenityResponseDto>>> find(AmenityFindDto query,
      Pageable pageable) {
    return this.amenityService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<AmenityResponseDto>> findById(@PathVariable Long id) {
    return this.amenityService.findById(id);
  }
}
