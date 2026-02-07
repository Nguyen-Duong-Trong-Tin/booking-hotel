package com.bookingHotel.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.CategoryConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.categories.CategoryCreateDto;
import com.bookingHotel.dtos.categories.CategoryFindDto;
import com.bookingHotel.dtos.categories.CategoryResponseDto;
import com.bookingHotel.dtos.categories.CategoryUpdateDto;
import com.bookingHotel.repositories.CategoryRepository;
import com.bookingHotel.repositories.entities.CategoryEntity;
import com.bookingHotel.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryConverter categoryConverter;

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public ResponseEntity<ResponseDto<CategoryResponseDto>> create(CategoryCreateDto body) {
    CategoryEntity categoryEntity = this.categoryConverter.toCategoryEntity(body);

    this.categoryRepository.save(categoryEntity);

    CategoryResponseDto categoryResponseDto = this.categoryConverter
        .toCategoryResponseDto(categoryEntity);
    return ResponseDto.created(categoryResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<CategoryResponseDto>> update(Long id, CategoryUpdateDto body) {
    CategoryEntity categoryEntity = this.categoryRepository.findById(id).orElse(null);
    if (categoryEntity == null) {
      return ResponseDto.notFound("Category not found");
    }

    this.categoryConverter.toCategoryEntity(body, categoryEntity);

    this.categoryRepository.save(categoryEntity);

    CategoryResponseDto categoryResponseDto = this.categoryConverter
        .toCategoryResponseDto(categoryEntity);
    return ResponseDto.success(categoryResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    CategoryEntity categoryEntity = this.categoryRepository.findById(id).orElse(null);
    if (categoryEntity == null) {
      return ResponseDto.notFound("Category not found");
    }

    this.categoryRepository.delete(categoryEntity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<CategoryResponseDto>>> find(CategoryFindDto query,
      Pageable pageable) {
    Specification<CategoryEntity> categorySpec = this.categoryRepository.hasCriteria(query);

    Page<CategoryEntity> categoryPage = this.categoryRepository.findAll(categorySpec, pageable);

    int page = categoryPage.getNumber();
    int size = categoryPage.getSize();
    int totalPages = categoryPage.getTotalPages();
    List<CategoryEntity> items = categoryPage.getContent();

    List<CategoryResponseDto> categoryResponseDtos = items.stream()
        .map(item -> this.categoryConverter.toCategoryResponseDto(item)).toList();
    ResponseSpecification<CategoryResponseDto> categoryResponseSpecification = ResponseSpecification
        .<CategoryResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(categoryResponseDtos)
        .build();
    return ResponseDto.success(categoryResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<CategoryResponseDto>> findById(Long id) {
    CategoryEntity categoryEntity = this.categoryRepository.findById(id).orElse(null);
    if (categoryEntity == null) {
      return ResponseDto.notFound("Category not found");
    }

    CategoryResponseDto categoryResponseDto = this.categoryConverter
        .toCategoryResponseDto(categoryEntity);
    return ResponseDto.success(categoryResponseDto);
  }
}
