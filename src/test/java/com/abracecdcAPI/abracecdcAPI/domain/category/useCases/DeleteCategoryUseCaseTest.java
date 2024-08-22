package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DeleteCategoryUseCases deleteCategoryUseCases;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should be able to delete a category")
    public void should_be_able_to_delete_a_category() {
        var categoryToDelete = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("categoryName")
                .description("categoryDescription")
                .build();

        when(categoryRepository.findById(categoryToDelete.getId())).thenReturn(Optional.of(categoryToDelete));

        deleteCategoryUseCases.execute(categoryToDelete.getId());

        ArgumentCaptor<CategoryEntity> categoryArgumentCaptor = ArgumentCaptor.forClass(CategoryEntity.class);
        verify(categoryRepository).delete(categoryArgumentCaptor.capture());
        CategoryEntity capturedCategory = categoryArgumentCaptor.getValue();

        assertEquals(categoryToDelete, capturedCategory);
        verify(categoryRepository, times(1)).findById(categoryToDelete.getId());
        verify(categoryRepository, times(1)).delete(categoryToDelete);
    }

    @Test
    @DisplayName("Should not be able to delete a non-existent category")
    public void should_not_be_able_to_delete_a_non_existent_category() {
        var categoryId = UUID.randomUUID();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> deleteCategoryUseCases.execute(categoryId));

        assertEquals("Category of event not found.", exception.getMessage());

        verify(categoryRepository, times(1)).findById(categoryId);
    }
}
