package com.abracecdcAPI.abracecdcAPI.domain.register_action.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;

public interface RegisterActionRepository extends JpaRepository<RegisterActionEntity, UUID> {
  
}
