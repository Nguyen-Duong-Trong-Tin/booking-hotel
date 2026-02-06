package com.bookingHotel.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.categories.CategoryFindDto;
import com.bookingHotel.dtos.categories.CategoryResponseDto;
import com.bookingHotel.dtos.roles.RoleCreateDto;
import com.bookingHotel.dtos.roles.RoleFindDto;
import com.bookingHotel.dtos.roles.RoleResponseDto;
import com.bookingHotel.dtos.roles.RoleUpdateDto;

public interface RoleService {
  ResponseEntity<ResponseDto<RoleResponseDto>> create(RoleCreateDto roleCreateDto);

  ResponseEntity<ResponseDto<RoleResponseDto>> update(Long id, RoleUpdateDto roleUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<RoleResponseDto>>> find(RoleFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<RoleResponseDto>> findById(Long id);
}
