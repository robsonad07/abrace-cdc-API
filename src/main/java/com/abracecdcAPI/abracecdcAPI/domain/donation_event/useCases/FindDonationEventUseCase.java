package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.DonationEventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindDonationEventUseCase {
    @Autowired
    private DonationEventRepository donationEventRepository;

    public DonationEvent execute(UUID id){
        Optional<DonationEvent> optionalDonationEvent = donationEventRepository.findById(id);
        if(optionalDonationEvent.isEmpty()){
            throw new DonationEventNotFoundException();
        }
        return optionalDonationEvent.get();
    }
}
