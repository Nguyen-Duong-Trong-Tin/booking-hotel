package com.bookingHotel.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.AmenityConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.amenities.AmenityCreateDto;
import com.bookingHotel.dtos.amenities.AmenityFindDto;
import com.bookingHotel.dtos.amenities.AmenityResponseDto;
import com.bookingHotel.dtos.amenities.AmenityUpdateDto;
import com.bookingHotel.repositories.AmenityRepository;
import com.bookingHotel.repositories.entities.AmenityEntity;
import com.bookingHotel.services.AmenityService;

@Service
public class AmenityServiceImpl implements AmenityService {
  @Autowired
  private AmenityConverter amenityConverter;

  @Autowired
  private AmenityRepository amenityRepository;

  @Override
  public ResponseEntity<ResponseDto<AmenityResponseDto>> create(AmenityCreateDto amenityCreateDto) {
    AmenityEntity amenityEntity = this.amenityConverter.toAmenityEntity(amenityCreateDto);

    amenityEntity = this.amenityRepository.save(amenityEntity);

    AmenityResponseDto amenityResponseDto = this.amenityConverter.toAmenityResponseDto(amenityEntity);

    return ResponseDto.success(amenityResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<AmenityResponseDto>> update(Long id, AmenityUpdateDto amenityUpdateDto) {
    AmenityEntity amenityEntity = this.amenityRepository.findById(id).orElse(null);

    if (amenityEntity == null) {
      return ResponseDto.notFound("Amenity not found");
    }

    this.amenityConverter.copyToAmenityEntity(amenityUpdateDto, amenityEntity);
    amenityEntity = this.amenityRepository.save(amenityEntity);

    AmenityResponseDto amenityResponseDto = this.amenityConverter.toAmenityResponseDto(amenityEntity);
    return ResponseDto.success(amenityResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    AmenityEntity amenityEntity = this.amenityRepository.findById(id).orElse(null);

    if (amenityEntity == null) {
      return ResponseDto.notFound("Amenity not found");
    }

    this.amenityRepository.delete(amenityEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<AmenityResponseDto>>> find(AmenityFindDto query,
      Pageable pageable) {
    Specification<AmenityEntity> amenitySpec = this.amenityRepository.hasCriteria(query);

    Page<AmenityEntity> amenityPage = this.amenityRepository.findAll(amenitySpec, pageable);

    int page = amenityPage.getNumber();
    int size = amenityPage.getSize();
    int totalPages = amenityPage.getTotalPages();
    List<AmenityEntity> items = amenityPage.getContent();

    List<AmenityResponseDto> amenityResponseDtos = items.stream()
        .map(item -> this.amenityConverter.toAmenityResponseDto(item)).toList();
    ResponseSpecification<AmenityResponseDto> amenityResponseSpecification = ResponseSpecification
        .<AmenityResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(amenityResponseDtos)
        .build();
    return ResponseDto.success(amenityResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<AmenityResponseDto>> findById(Long id) {
    AmenityEntity amenityEntity = this.amenityRepository.findById(id).orElse(null);
    if (amenityEntity == null) {
      return ResponseDto.notFound("Amenity not found");
    }

    AmenityResponseDto amenityResponseDto = this.amenityConverter.toAmenityResponseDto(amenityEntity);
    return ResponseDto.success(amenityResponseDto);
  }
}
