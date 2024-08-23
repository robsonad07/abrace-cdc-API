package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;

@Service
public class UpdateCategoryUseCase {
    @Autowired
    private CategoryRepository categoryRepository;  

    public CategoryEntity execute(CategoryEntity categoryEntity) {  
        Optional<CategoryEntity> categoryOptional = this.categoryRepository.findById(categoryEntity.getId());
        
        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        var existingCategory = categoryOptional.get();
        existingCategory.setName(categoryEntity.getName());
        existingCategory.setDescription(categoryEntity.getDescription());

        var result = categoryRepository.save(existingCategory);

        return result;
    }
}
