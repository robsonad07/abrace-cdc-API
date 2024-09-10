package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;

@Service
public class ListAllCategories {
  
  @Autowired
  private CategoryRepository categoryRepository;

  public List<CategoryEntity> execute() {
    return categoryRepository.findAll();
  }
}
