package com.abracecdcAPI.abracecdcAPI.domain.category_action.controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.category_action.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.useCases.CreateCategoryUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.useCases.DeleteCategoryUseCases;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.useCases.ListAllCategoryByFilterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.useCases.UpdateCategoryUseCase;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Autowired
    private ListAllCategoryByFilterUseCase listAllCategoryByFilterUseCase;
    
    @Autowired
    private DeleteCategoryUseCases deleteCategoryUseCases;


    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryEntity categoryEntity) {
        try {
            var result =createCategoryUseCase.execute(categoryEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
        public ResponseEntity<Object> updateCategory(@RequestBody CategoryEntity categoryEntity) {
        try {
            var result = updateCategoryUseCase.execute(categoryEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getCategoryByName(@RequestParam String filter) {
        try {
            var result = listAllCategoryByFilterUseCase.execute(filter);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{categoryIdToDelete}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID categoryIdToDelete) {
        try{
            deleteCategoryUseCases.execute(categoryIdToDelete);
            return ResponseEntity.noContent().build();
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}