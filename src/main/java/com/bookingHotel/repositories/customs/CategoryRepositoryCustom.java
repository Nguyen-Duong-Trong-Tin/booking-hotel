package com.bookingHotel.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.bookingHotel.dtos.categories.CategoryFindDto;
import com.bookingHotel.repositories.entities.CategoryEntity;

public interface CategoryRepositoryCustom {
  Specification<CategoryEntity> hasCriteria(CategoryFindDto query);
}
