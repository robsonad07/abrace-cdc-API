package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should be able to get a category by its ID")
    public void should_be_able_to_get_a_category_by_id() {
        var categoryToGetById = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("categoryName")
                .description("categoryDescription")
                .build();

        when(categoryRepository.findById(categoryToGetById.getId())).thenReturn(Optional.of(categoryToGetById));

        CategoryEntity result = getCategoryByIdUseCase.execute(categoryToGetById.getId());

        assertEquals(categoryToGetById, result);
        verify(categoryRepository).findById(categoryToGetById.getId());
    }

    @Test
    @DisplayName("Should throw CategoryNotFoundException if the category does not exist")
    public void should_throw_category_not_found_exception_if_category_does_not_exist() {
        UUID nonExistentCategoryId = UUID.randomUUID();

        when(categoryRepository.findById(nonExistentCategoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> getCategoryByIdUseCase.execute(nonExistentCategoryId));
    
        assertEquals("Category of event not found.", exception.getMessage());
    
        verify(categoryRepository, times(1)).findById(nonExistentCategoryId);
    }

}
