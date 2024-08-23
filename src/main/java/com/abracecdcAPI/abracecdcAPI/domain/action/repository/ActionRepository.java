package com.abracecdcAPI.abracecdcAPI.domain.action.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;

public interface ActionRepository extends JpaRepository<ActionEntity, UUID>{
  Optional<ActionEntity> findByTitleAndSubtitle(String title, String subtitle);
  List<ActionEntity> findByTitle(String title);
}
