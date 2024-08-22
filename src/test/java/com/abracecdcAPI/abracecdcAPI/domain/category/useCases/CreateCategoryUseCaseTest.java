package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.exceptions.DuplicateCategoryException;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void should_be_able_to_create_a_category() {
        var categoryToCreate = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("categoryName")
                .description("categoryDescription")
                .build();

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryToCreate);

        var categoryCreated = createCategoryUseCase.execute(categoryToCreate);

        assertEquals(categoryToCreate, categoryCreated);
    }

    @Test
    public void should_not_create_category_if_already_exists() {
        var categoryToCreate = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("categoryName")
                .description("categoryDescription")
                .build();

        when(categoryRepository.existsByName(categoryToCreate.getName())).thenReturn(true);

        DuplicateCategoryException exception = assertThrows(DuplicateCategoryException.class,
                () -> createCategoryUseCase.execute(categoryToCreate));

        assertEquals("Category with name " + categoryToCreate.getName() + " already exists.", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_repository_fails() {
        var categoryToCreate = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("categoryName")
                .description("categoryDescription")
                .build();

        when(categoryRepository.save(any(CategoryEntity.class))).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> createCategoryUseCase.execute(categoryToCreate));

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    public void should_throw_exception_when_category_name_is_null() {
        var categoryToCreate = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .description("categoryDescription")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createCategoryUseCase.execute(categoryToCreate));

        assertTrue(exception.getMessage().contains("Category name cannot be null or empty"));
    }
}
