package com.abracecdcAPI.abracecdcAPI.domain.event.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByTitleAndCaption(String title, String caption);

    Page<Event> findAllByOrderByDateTimeDesc(Pageable pageable);
}
