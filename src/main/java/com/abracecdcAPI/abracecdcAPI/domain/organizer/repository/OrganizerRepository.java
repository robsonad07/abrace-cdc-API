package com.abracecdcAPI.abracecdcAPI.domain.organizer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;


public interface OrganizerRepository extends JpaRepository<OrganizerEntity, UUID> {
  List<OrganizerEntity> findByName(String name);
  Optional<OrganizerEntity> findByEmail(String email);
  Optional<OrganizerEntity> findByCellphone(String cellphone);
}
