package com.bookingHotel.services.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookingHotel.constants.Constant;
import com.bookingHotel.converters.RoomImageConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.roomImages.RoomImageCreateAllDto;
import com.bookingHotel.dtos.roomImages.RoomImageResponseDto;
import com.bookingHotel.repositories.RoomImageRepository;
import com.bookingHotel.repositories.entities.RoomImageEntity;
import com.bookingHotel.services.CloudinaryService;
import com.bookingHotel.services.RoomImageService;

@Service
public class RoomImageServiceImpl implements RoomImageService {
  @Autowired
  private RoomImageConverter roomImageConverter;

  @Autowired
  private CloudinaryService cloudinaryService;

  @Autowired
  private RoomImageRepository roomImageRepository;

  @Override
  public ResponseEntity<ResponseDto<List<RoomImageResponseDto>>> createAll(RoomImageCreateAllDto roomImageCreateAllDto,
      List<MultipartFile> images) {
    Integer presentativeIndex = roomImageCreateAllDto.getPresentativeIndex();
    Long roomId = roomImageCreateAllDto.getRoomId();

    if (presentativeIndex != null && (presentativeIndex < 0 || presentativeIndex >= images.size())) {
      return ResponseDto.badRequest(List.of("Presentative index is out of range"));
    }

    Integer existingImageCount = this.roomImageRepository.countByRoomId(roomImageCreateAllDto.getRoomId());
    if (existingImageCount + images.size() > Constant.MAX_ROOM_IMAGES) {
      List<String> errors = new ArrayList<>();
      errors.add("A room can have a maximum of " + Constant.MAX_ROOM_IMAGES + " images.");
      return ResponseDto.badRequest(errors);
    }

    boolean hasExistingPresentative = this.roomImageRepository.existsByRoomIdAndIsPresentativeTrue(roomId);
    if (presentativeIndex != null) {
      List<RoomImageEntity> existingImages = this.roomImageRepository.findByRoomId(roomId);
      for (RoomImageEntity existingImage : existingImages) {
        existingImage.setIsPresentative(false);
      }
      if (!existingImages.isEmpty()) {
        this.roomImageRepository.saveAll(existingImages);
      }
      hasExistingPresentative = false;
    }

    List<String> urls = images.stream().map((image) -> this.cloudinaryService.uploadFile(image)).toList();

    List<RoomImageEntity> roomImageEntities = new ArrayList<>();
    for (int i = 0; i < urls.size(); i++) {
      RoomImageEntity roomImageEntity = this.roomImageConverter.toRoomImageEntity(
          roomImageCreateAllDto, urls.get(i));
      if (presentativeIndex != null && i == presentativeIndex) {
        roomImageEntity.setIsPresentative(true);
      } else if (presentativeIndex == null && !hasExistingPresentative && i == 0) {
        roomImageEntity.setIsPresentative(true);
      }
      roomImageEntities.add(roomImageEntity);
    }
    roomImageEntities = this.roomImageRepository.saveAll(roomImageEntities);

    List<RoomImageResponseDto> roomImageResponseDtos = new ArrayList<>();
    for (RoomImageEntity roomImageEntity : roomImageEntities) {
      RoomImageResponseDto roomImageResponseDto = this.roomImageConverter
          .toRoomImageResponseDto(roomImageEntity);

      roomImageResponseDtos.add(roomImageResponseDto);
    }

    return ResponseDto.success(roomImageResponseDtos);
  }

  @Override
  public ResponseEntity<ResponseDto<RoomImageResponseDto>> setPresentative(Long id) {
    RoomImageEntity roomImageEntity = this.roomImageRepository.findById(id).orElse(null);
    if (roomImageEntity == null) {
      return ResponseDto.notFound("Room image not found");
    }

    Long roomId = roomImageEntity.getRoom().getId();
    List<RoomImageEntity> existingImages = this.roomImageRepository.findByRoomId(roomId);
    for (RoomImageEntity existingImage : existingImages) {
      existingImage.setIsPresentative(existingImage.getId().equals(id));
    }
    this.roomImageRepository.saveAll(existingImages);

    RoomImageResponseDto roomImageResponseDto = this.roomImageConverter.toRoomImageResponseDto(roomImageEntity);
    roomImageResponseDto.setIsPresentative(true);

    return ResponseDto.success(roomImageResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    RoomImageEntity roomImageEntity = this.roomImageRepository.findById(id).orElse(null);
    if (roomImageEntity == null) {
      return ResponseDto.notFound("Room image not found");
    }

    this.roomImageRepository.delete(roomImageEntity);
    return ResponseDto.success(null);
  }

  @Override
  public void deleteByRoomId(Long roomId) {
    this.roomImageRepository.deleteByRoomId(roomId);
  }
}
