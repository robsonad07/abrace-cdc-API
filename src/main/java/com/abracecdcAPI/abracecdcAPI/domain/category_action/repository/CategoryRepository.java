package com.abracecdcAPI.abracecdcAPI.domain.category_action.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.category_action.entity.CategoryEntity;



public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    List<CategoryEntity> findByName(String name);

    @Override
    public CategoryEntity save(CategoryEntity CategoryEntity);

    @Override
    public void delete(CategoryEntity existingCategoryEvent);
}