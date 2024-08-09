package com.abracecdcAPI.abracecdcAPI.domain.category_action.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.category_action.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;



@Service
public class DeleteCategoryUseCases {

    @Autowired
    private CategoryRepository categoryRepository;

    public void execute(UUID categoryEventId) {
        Optional<CategoryEntity> categoryOptional =
         this.categoryRepository.findById(categoryEventId);

        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        var existingCategoryEvent = categoryOptional.get();

        this.categoryRepository.delete(existingCategoryEvent);
        
    }
}