package com.abracecdcAPI.abracecdcAPI.domain.organizer_event.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.entity.OrganizerEventEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases.CreateOrganizerEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases.DeleteOrganizerEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases.ListAllOrganizerEventByFilterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases.UpdateOrganizerEventUseCase;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerEventNotFoundException;

@RestController
@RequestMapping("/organizer-event")
public class OrganizerEventController {

  @Autowired
  private CreateOrganizerEventUseCase createOrganizerEventUseCase;

  @Autowired
  private UpdateOrganizerEventUseCase updateOrganizerEventUseCase;
  
  @Autowired
  private DeleteOrganizerEventUseCase deleteOrganizerEventUseCase;

  @Autowired
  private ListAllOrganizerEventByFilterUseCase listAllOrganizerEventByFilterUseCase;

  @PostMapping("/create")
  public ResponseEntity<Object> createOrganizerEvent(@RequestBody OrganizerEventEntity organizerEventEntity) {
    try {
      var result = createOrganizerEventUseCase.execute(organizerEventEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Object> updateOrganizerEvent(@RequestBody OrganizerEventEntity organizerEventEntity) {
    try {
      var result = updateOrganizerEventUseCase.execute(organizerEventEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/list")
  public ResponseEntity<Object> getOrganizerEventByName(@RequestParam String filter) {
    try {
      var result = listAllOrganizerEventByFilterUseCase.execute(filter);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/delete/{organizerEventIdToDelete}")
  public ResponseEntity<Object> deleteOrganizerEventById(@PathVariable UUID organizerEventIdToDelete) {
    try {
      deleteOrganizerEventUseCase.execute(organizerEventIdToDelete);
      return ResponseEntity.noContent().build();
    } catch (OrganizerEventNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
