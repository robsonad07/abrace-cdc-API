package com.abracecdcAPI.abracecdcAPI.domain.event.repository;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByTitleAndCaption(String title, String caption);
}