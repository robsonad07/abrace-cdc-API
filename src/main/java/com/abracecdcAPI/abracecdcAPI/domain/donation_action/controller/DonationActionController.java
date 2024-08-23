package com.abracecdcAPI.abracecdcAPI.domain.donation_action.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.donation_action.dto.CreateDonationActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.dto.UpdateDonationActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.exceptions.DonationActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases.CreateDonationActionUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases.DeleteDonationActionUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases.GetDonationActionByIdUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases.UpdateDonationActionUseCase;

@RestController
@RequestMapping("/donation-action")
public class DonationActionController {
  
  @Autowired
  private CreateDonationActionUseCase createDonationActionUseCase;

  @Autowired
  private GetDonationActionByIdUseCase getDonationActionByIdUseCase;

  @Autowired
  private UpdateDonationActionUseCase updateDonationActionUseCase;

  @Autowired 
  private DeleteDonationActionUseCase deleteDonationActionUseCase;

  @PostMapping("/create")
  public ResponseEntity<Object> createDonationAction(@RequestBody CreateDonationActionDTO createDonationActionDTO) {
      try {
        var donationActionCreated = this.createDonationActionUseCase.execute(createDonationActionDTO);
        return ResponseEntity.ok().body(donationActionCreated);
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @GetMapping("/{createDonationId}")
  public ResponseEntity<Object> getDonationAction(@PathVariable UUID createDonationId) {
    try {
      var donationAction = this.getDonationActionByIdUseCase.execute(createDonationId);
      return ResponseEntity.ok().body(donationAction);
    } catch (DonationActionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Object> updateDonationAction(@RequestBody UpdateDonationActionDTO updateDonationActionDTO) {
      try {
        var donationAction = this.updateDonationActionUseCase.execute(updateDonationActionDTO);
        return ResponseEntity.ok().body(donationAction);
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @DeleteMapping("/{createDonationId}")
  public ResponseEntity<Object> deleteOrganizerById(@PathVariable UUID createDonationId) {
    try {
      deleteDonationActionUseCase.execute(createDonationId);
      return ResponseEntity.noContent().build();
    } catch (DonationActionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
  
}
