package com.abracecdcAPI.abracecdcAPI.domain.category_action.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.category_action.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.repository.CategoryRepository;



@Service
public class CreateCategoryUseCase {
    @Autowired 
    private CategoryRepository categoryRepository;

    public CategoryEntity execute(CategoryEntity CategoryEntity) {
        return this.categoryRepository.save(CategoryEntity);
    }
}