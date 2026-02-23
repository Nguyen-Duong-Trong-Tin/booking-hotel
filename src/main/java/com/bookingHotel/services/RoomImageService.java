package com.bookingHotel.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.roomImages.RoomImageCreateAllDto;
import com.bookingHotel.dtos.roomImages.RoomImageResponseDto;

public interface RoomImageService {
  ResponseEntity<ResponseDto<List<RoomImageResponseDto>>> createAll(
      RoomImageCreateAllDto roomImageCreateAllDto, List<MultipartFile> images);
}
