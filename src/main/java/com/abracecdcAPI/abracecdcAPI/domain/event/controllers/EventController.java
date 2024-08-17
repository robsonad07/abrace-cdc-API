package com.abracecdcAPI.abracecdcAPI.domain.event.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.CreateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.FindEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.GetAllEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.UpdateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.dto.EventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class EventController {
    @Autowired
    CreateEventUseCase createEventUseCase;
    @Autowired
    GetAllEventUseCase getAllEventUseCase;
    @Autowired
    FindEventUseCase findEventUseCase;
    @Autowired
    UpdateEventUseCase updateEventUseCase;

    @PostMapping("/event")
    public ResponseEntity<Object> createEvent(@RequestBody @Valid EventDTO eventDTO){
        try{
            Event event = createEventUseCase.execute(eventDTO);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/events")
    public ResponseEntity<Object> getAllEvents() {
        try{
            List<Event> events = getAllEventUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Object> getOneEvent(@PathVariable(value = "id") UUID id){
        try{
            Event event = findEventUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable(value = "id") UUID id, @RequestBody @Valid EventDTO eventDTO){
        try {
            Event event = updateEventUseCase.execute(id, eventDTO);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
