package com.bookingHotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookingHotel.constants.Constant;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.roomImages.RoomImageCreateAllDto;
import com.bookingHotel.dtos.roomImages.RoomImageResponseDto;
import com.bookingHotel.services.RoomImageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/room-images")
public class RoomImageController {
  @Autowired
  private RoomImageService roomImageService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseDto<List<RoomImageResponseDto>>> createAll(
      @Valid @ModelAttribute RoomImageCreateAllDto roomImageCreateAllDto,
      @RequestParam List<MultipartFile> images) {
    if (images.size() > Constant.MAX_ROOM_IMAGES) {
      return ResponseDto.badRequest(List.of("Too many images"));
    }

    return this.roomImageService.createAll(roomImageCreateAllDto, images);
  }
}
