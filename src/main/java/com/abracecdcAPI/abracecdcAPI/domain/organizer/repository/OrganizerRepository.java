package com.abracecdcAPI.abracecdcAPI.domain.organizer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;

import java.util.List;


public interface OrganizerRepository extends JpaRepository<OrganizerEntity, UUID> {
  List<OrganizerEntity> findByName(String name);
}
