package com.abracecdcAPI.abracecdcAPI.domain.event.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.CreateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.GetAllEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.dto.EventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    CreateEventUseCase createEventUseCase;
    @Autowired
    GetAllEventUseCase getAllEventUseCase;

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
}
