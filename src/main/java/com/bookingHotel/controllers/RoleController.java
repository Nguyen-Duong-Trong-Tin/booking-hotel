package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.roles.RoleCreateDto;
import com.bookingHotel.dtos.roles.RoleFindDto;
import com.bookingHotel.dtos.roles.RoleResponseDto;
import com.bookingHotel.dtos.roles.RoleUpdateDto;
import com.bookingHotel.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @PostMapping
  public ResponseEntity<ResponseDto<RoleResponseDto>> create(@Valid RoleCreateDto roleCreateDto) {
    return this.roleService.create(roleCreateDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<RoleResponseDto>> update(@PathVariable Long id,
      @Valid RoleUpdateDto roleUpdateDto) {
    return this.roleService.update(id, roleUpdateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.roleService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<RoleResponseDto>>> find(RoleFindDto query,
      Pageable pageable) {
    return this.roleService.find(query, pageable);
  }

  public ResponseEntity<ResponseDto<RoleResponseDto>> findById(Long id) {
    return this.roleService.findById(id);
  }
}
