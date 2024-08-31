package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.exceptions.DuplicateCategoryException;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;

@Service
public class CreateCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity executeSave(CategoryEntity categoryEntity) {
        return this.categoryRepository.save(categoryEntity);
    }

    public CategoryEntity execute(CategoryEntity category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }

        if (categoryRepository.existsByName(category.getName())) {
            throw new DuplicateCategoryException("Category with name " + category.getName() + " already exists.");
        }

        return categoryRepository.save(category);
    }
}
