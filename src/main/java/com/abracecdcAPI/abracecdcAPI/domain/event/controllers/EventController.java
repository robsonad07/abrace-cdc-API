package com.abracecdcAPI.abracecdcAPI.domain.event.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.CreateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.DeleteEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.FindEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.GetAllEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.GetLastEventsUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.UpdateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.dto.EventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private CreateEventUseCase createEventUseCase;

    @Autowired
    private GetAllEventUseCase getAllEventUseCase;

    @Autowired
    private GetLastEventsUseCase getLatestEventsUseCase;

    @Autowired
    private FindEventUseCase findEventUseCase;

    @Autowired
    private UpdateEventUseCase updateEventUseCase;

    @Autowired
    private DeleteEventUseCase deleteEventUseCase;

    @PostMapping
    public ResponseEntity<Object> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        try {
            Event event = createEventUseCase.execute(eventDTO);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllEvents() {
        try {
            List<Event> events = getAllEventUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<Object> getLatestEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Event> events = getLatestEventsUseCase.execute(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEvent(@PathVariable(value = "id") UUID id) {
        try {
            Event event = findEventUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable(value = "id") UUID id, @RequestBody @Valid EventDTO eventDTO) {
        try {
            Event event = updateEventUseCase.execute(id, eventDTO);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable(value = "id") UUID id) {
        try {
            String msg = deleteEventUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
