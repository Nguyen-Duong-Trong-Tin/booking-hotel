package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.categories.CategoryCreateDto;
import com.bookingHotel.dtos.categories.CategoryFindDto;
import com.bookingHotel.dtos.categories.CategoryResponseDto;
import com.bookingHotel.dtos.categories.CategoryUpdateDto;

public interface CategoryService {
  ResponseEntity<ResponseDto<CategoryResponseDto>> create(CategoryCreateDto body);

  ResponseEntity<ResponseDto<CategoryResponseDto>> update(Long id, CategoryUpdateDto body);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<CategoryResponseDto>>> find(CategoryFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<CategoryResponseDto>> findById(Long id);
}
