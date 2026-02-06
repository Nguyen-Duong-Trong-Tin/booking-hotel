package com.bookingHotel.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.dtos.categories.CategoryCreateDto;
import com.bookingHotel.dtos.categories.CategoryResponseDto;
import com.bookingHotel.dtos.categories.CategoryUpdateDto;
import com.bookingHotel.repositories.entities.CategoryEntity;

@Component
public class CategoryConverter {
  @Autowired
  private ModelMapper modelMapper;

  public CategoryEntity toCategoryEntity(CategoryCreateDto categoryCreateDto) {
    return this.modelMapper.map(categoryCreateDto, CategoryEntity.class);
  }

  public CategoryResponseDto toCategoryResponseDto(CategoryEntity categoryEntity) {
    return this.modelMapper.map(categoryEntity, CategoryResponseDto.class);
  }

  public void toCategoryEntity(CategoryUpdateDto categoryUpdateDto, CategoryEntity categoryEntity) {
    this.modelMapper.map(categoryUpdateDto, categoryEntity);
  }
}
