package com.bookingHotel.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.annotations.Auth;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.categories.CategoryCreateDto;
import com.bookingHotel.dtos.categories.CategoryFindDto;
import com.bookingHotel.dtos.categories.CategoryResponseDto;
import com.bookingHotel.dtos.categories.CategoryUpdateDto;
import com.bookingHotel.services.CategoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @PostMapping()
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<CategoryResponseDto>> create(@Valid @RequestBody CategoryCreateDto body) {
    return this.categoryService.create(body);
  }

  @PatchMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<CategoryResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody CategoryUpdateDto body) {
    return this.categoryService.update(id, body);
  }

  @DeleteMapping("/{id}")
  @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.categoryService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<CategoryResponseDto>>> find(
      CategoryFindDto query,
      Pageable pageable) {
    return this.categoryService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<CategoryResponseDto>> findById(@PathVariable Long id) {
    return this.categoryService.findById(id);
  }
}
