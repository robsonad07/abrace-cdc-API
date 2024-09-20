package com.abracecdcAPI.abracecdcAPI.domain.category.controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.category.dto.CategoryDTO;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.useCases.CreateCategoryUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.category.useCases.DeleteCategoryUseCases;
import com.abracecdcAPI.abracecdcAPI.domain.category.useCases.ListAllCategories;
import com.abracecdcAPI.abracecdcAPI.domain.category.useCases.ListAllCategoryByFilterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.category.useCases.UpdateCategoryUseCase;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class CategoryController {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Autowired
    private ListAllCategoryByFilterUseCase listAllCategoryByFilterUseCase;

    @Autowired
    private ListAllCategories listAllCategories;
    
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
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) {
        try {
            var result = updateCategoryUseCase.execute(id, categoryDTO);
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

    @GetMapping("/")
    public ResponseEntity<Object> listAllCategories() {
        try {
            var result = listAllCategories.execute();
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