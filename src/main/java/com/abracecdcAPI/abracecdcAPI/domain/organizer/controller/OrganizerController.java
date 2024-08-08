package com.abracecdcAPI.abracecdcAPI.domain.organizer.controller;

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

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases.CreateOrganizerUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases.DeleteOrganizerUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases.ListAllOrganizerByFilterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases.UpdateOrganizerUseCase;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@RestController
@RequestMapping("/organizer")
public class OrganizerController {

  @Autowired
  private CreateOrganizerUseCase createOrganizerUseCase;

  @Autowired
  private UpdateOrganizerUseCase updateOrganizerUseCase;
  
  @Autowired
  private DeleteOrganizerUseCase deleteOrganizerUseCase;

  @Autowired
  private ListAllOrganizerByFilterUseCase listAllOrganizerByFilterUseCase;

  @PostMapping("/create")
  public ResponseEntity<Object> createOrganizer(@RequestBody OrganizerEntity organizerEntity) {
    try {
      var result = createOrganizerUseCase.execute(organizerEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Object> updateOrganizer(@RequestBody OrganizerEntity organizerEntity) {
    try {
      var result = updateOrganizerUseCase.execute(organizerEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/list")
  public ResponseEntity<Object> getOrganizerByName(@RequestParam String filter) {
    try {
      var result = listAllOrganizerByFilterUseCase.execute(filter);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/delete/{organizerIdToDelete}")
  public ResponseEntity<Object> deleteOrganizerById(@PathVariable UUID organizerIdToDelete) {
    try {
      deleteOrganizerUseCase.execute(organizerIdToDelete);
      return ResponseEntity.noContent().build();
    } catch (OrganizerNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
