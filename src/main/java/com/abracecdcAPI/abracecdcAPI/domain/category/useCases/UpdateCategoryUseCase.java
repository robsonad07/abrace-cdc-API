package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.category.dto.CategoryDTO;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity execute(UUID id, CategoryDTO categoryDTO) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        var existingCategory = categoryOptional.get();

        if (categoryDTO.getName() != null) {
            existingCategory.setName(categoryDTO.getName());
        }

        if (categoryDTO.getDescription() != null) {
            existingCategory.setDescription(categoryDTO.getDescription());
        }

        return categoryRepository.save(existingCategory);
    }
}

