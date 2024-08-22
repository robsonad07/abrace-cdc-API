package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.category.dto.CategoryDTO;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should be able to update an existing category")
    public void should_be_able_to_update_an_existing_category() {
        var idCategory = UUID.randomUUID();

        var existingCategory = CategoryEntity.builder()
                .id(idCategory)
                .name("Old Category")
                .description("Old Description")
                .build();

        var updatedCategory = CategoryEntity.builder()
                .id(idCategory)
                .name("Updated Category")
                .description("Updated Description")
                .build();

        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(updatedCategory);

        CategoryDTO categoryDTO = new CategoryDTO("Updated Category", "Updated Description");
        var result = updateCategoryUseCase.execute(idCategory, categoryDTO);

        ArgumentCaptor<CategoryEntity> categoryArgumentCaptor = ArgumentCaptor.forClass(CategoryEntity.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        CategoryEntity capturedCategory = categoryArgumentCaptor.getValue();

        assertEquals(updatedCategory, result);
        assertEquals("Updated Category", capturedCategory.getName());
        assertEquals("Updated Description", capturedCategory.getDescription());

        verify(categoryRepository, times(1)).findById(idCategory);
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("Should throw CategoryNotFoundException if the category does not exist")
    public void should_throw_category_not_found_exception_if_category_does_not_exist() {
        UUID nonExistentCategoryId = UUID.randomUUID();
        CategoryDTO categoryDTO = new CategoryDTO("Updated Category", "Updated Description");

        when(categoryRepository.findById(nonExistentCategoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> updateCategoryUseCase.execute(nonExistentCategoryId, categoryDTO));

        assertEquals("Category of event not found.", exception.getMessage());

        verify(categoryRepository, times(1)).findById(nonExistentCategoryId);
        verify(categoryRepository, times(0)).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("Should update only the name and keep the description unchanged")
    public void should_update_name_only() {
        var idCategory = UUID.randomUUID();

        var existingCategory = CategoryEntity.builder()
                .id(idCategory)
                .name("Old Category")
                .description("Old Description")
                .build();

        var updatedCategory = CategoryEntity.builder()
                .id(idCategory)
                .name("Updated Category")
                .description("Old Description")
                .build();

        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(updatedCategory);

        CategoryDTO categoryDTO = new CategoryDTO("Updated Category", null);
        updateCategoryUseCase.execute(idCategory, categoryDTO);

        ArgumentCaptor<CategoryEntity> categoryArgumentCaptor = ArgumentCaptor.forClass(CategoryEntity.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        CategoryEntity capturedCategory = categoryArgumentCaptor.getValue();

        assertEquals("Updated Category", capturedCategory.getName());
        assertEquals("Old Description", capturedCategory.getDescription());

        verify(categoryRepository, times(1)).findById(idCategory);
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("Should not change the category if the DTO has the same values")
    public void should_not_change_category_if_same_values() {
        var idCategory = UUID.randomUUID();

        var existingCategory = CategoryEntity.builder()
                .id(idCategory)
                .name("Old Category")
                .description("Old Description")
                .build();

        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        CategoryDTO categoryDTO = new CategoryDTO("Old Category", "Old Description");
        var result = updateCategoryUseCase.execute(idCategory, categoryDTO);

        verify(categoryRepository, times(1)).findById(idCategory);
        verify(categoryRepository, times(1)).save(existingCategory);

        assertEquals(existingCategory, result);
    }

}
