package com.abracecdcAPI.abracecdcAPI.domain.event.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.CreateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.dto.EventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @Autowired
    CreateEventUseCase createEventUseCase;

    @PostMapping("/event")
    public ResponseEntity<Object> createEvent(@RequestBody @Valid EventDTO eventDTO){
        try{
            Event event = createEventUseCase.execute(eventDTO);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
