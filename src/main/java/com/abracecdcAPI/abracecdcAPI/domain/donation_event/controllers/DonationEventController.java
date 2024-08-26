package com.abracecdcAPI.abracecdcAPI.domain.donation_event.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto.DonationEventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class DonationEventController {
    @Autowired
    private CreateDonationEventUseCase createDonationEventUseCase;
    @Autowired
    private GetAllDonationEventUseCase getAllDonationEventUseCase;
    @Autowired
    private FindDonationEventUseCase findDonationEventUseCase;
    @Autowired
    private UpdateDonationEventUseCase updateDonationEventUseCase;
    @Autowired
    private DeleteDonationUseCase deleteDonationUseCase;

    @PostMapping("/donation-event")
    public ResponseEntity<Object> createDonationEvent(@RequestBody @Valid DonationEventDTO donationEventDTO){
        try{
            DonationEvent donationEvent = createDonationEventUseCase.execute(donationEventDTO);
            return ResponseEntity.status(HttpStatus.OK).body(donationEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/donation-events")
    public  ResponseEntity<Object> getAllDonationEvent(){
        try{
            List<DonationEvent> donationEvents = getAllDonationEventUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(donationEvents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/donation-event/{id}")
    public ResponseEntity<Object> findDonationEvent(@PathVariable(value = "id") UUID id){
        try{
            DonationEvent donationEvent = findDonationEventUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(donationEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/donation-event/{id}")
    public ResponseEntity<Object> updateDonationEvent(@PathVariable(value = "id") UUID id, @RequestBody @Valid DonationEventDTO donationEventDTO){
        try{
            DonationEvent donationEvent = updateDonationEventUseCase.execute(id, donationEventDTO);
            return ResponseEntity.status(HttpStatus.OK).body(donationEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/donation-event/{id}")
    public ResponseEntity<Object> deleteDonationEvent(@PathVariable(value = "id") UUID id){
        try{
            String msm = deleteDonationUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(msm);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
