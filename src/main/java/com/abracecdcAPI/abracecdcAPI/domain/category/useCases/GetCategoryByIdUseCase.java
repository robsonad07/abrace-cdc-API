package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;

@Service
public class GetCategoryByIdUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity execute(UUID idCategory) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(idCategory);

        if(categoryOptional.isEmpty()){
            throw new CategoryNotFoundException();
        }
        return categoryOptional.get();
    }
}