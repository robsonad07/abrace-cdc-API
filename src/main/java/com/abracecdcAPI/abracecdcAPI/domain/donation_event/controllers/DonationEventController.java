package com.abracecdcAPI.abracecdcAPI.domain.donation_event.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto.DonationEventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases.CreateDonationEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases.GetAllDonationEventUseCase;
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
public class DonationEventController {
    @Autowired
    private CreateDonationEventUseCase createDonationEventUseCase;
    @Autowired
    private GetAllDonationEventUseCase getAllDonationEventUseCase;

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
}
