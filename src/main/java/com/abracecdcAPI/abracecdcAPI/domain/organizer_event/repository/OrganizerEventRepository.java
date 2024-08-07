package com.abracecdcAPI.abracecdcAPI.domain.organizer_event.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.entity.OrganizerEventEntity;
import java.util.List;


public interface OrganizerEventRepository extends JpaRepository<OrganizerEventEntity, UUID> {
  List<OrganizerEventEntity> findByName(String name);
}
