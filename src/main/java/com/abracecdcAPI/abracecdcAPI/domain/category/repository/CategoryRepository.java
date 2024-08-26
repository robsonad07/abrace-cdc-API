package com.abracecdcAPI.abracecdcAPI.domain.category.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    List<CategoryEntity> findByNameContainingIgnoreCase(String name);

    @Override
    CategoryEntity save(CategoryEntity categoryEntity);

    @Override
    void delete(CategoryEntity categoryEntity);

    boolean existsByName(String name);

    @Override
    void deleteById(UUID id);
}
