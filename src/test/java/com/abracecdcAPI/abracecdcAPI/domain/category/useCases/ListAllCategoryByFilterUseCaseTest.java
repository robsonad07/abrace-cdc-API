package com.abracecdcAPI.abracecdcAPI.domain.category.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ListAllCategoryByFilterUseCaseTest {

    @InjectMocks
    private ListAllCategoryByFilterUseCase listAllCategoryByFilterUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should return all categories matching the given filter")
    public void should_return_all_categories_matching_filter() {
        var idCategory1 = UUID.randomUUID();
        var idCategory2 = UUID.randomUUID();

        var category1 = CategoryEntity.builder()
                .id(idCategory1)
                .name("Technology")
                .description("All about technology")
                .build();

        var category2 = CategoryEntity.builder()
                .id(idCategory2)
                .name("Science")
                .description("All about science")
                .build();

        var categoryList = Stream.of(category1, category2).collect(Collectors.toList());

        when(categoryRepository.findByNameContainingIgnoreCase("tech")).thenReturn(categoryList);

        var result = listAllCategoryByFilterUseCase.execute("tech");

        assertEquals(categoryList, result);

        verify(categoryRepository, times(1)).findByNameContainingIgnoreCase("tech");
    }

    @Test
    @DisplayName("Should return empty list if no categories match the given filter")
    public void should_return_empty_list_if_no_categories_match_filter() {
        when(categoryRepository.findByNameContainingIgnoreCase("non-existent")).thenReturn(List.of());

        var result = listAllCategoryByFilterUseCase.execute("non-existent");

        assertTrue(result.isEmpty());

        verify(categoryRepository, times(1)).findByNameContainingIgnoreCase("non-existent");
    }
}
