package com.bookingHotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookingHotel.repositories.customs.CategoryRepositoryCustom;
import com.bookingHotel.repositories.entities.CategoryEntity;

public interface CategoryRepository
    extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity>, CategoryRepositoryCustom {

}
