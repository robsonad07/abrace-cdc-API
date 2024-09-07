package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto.DonationEventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.UserNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.ValueOfDonationEventIsNegativeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateDonationEventUseCase {
    @Autowired
    private DonationEventRepository donationEventRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    public DonationEvent execute(DonationEventDTO donationEventDTO){
        Optional<Event> optionalEvent = eventRepository.findById(donationEventDTO.event_id());
        Optional<User> optionalUser = userRepository.findById(donationEventDTO.user_id());
        if(donationEventDTO.value() < 0){
            throw new ValueOfDonationEventIsNegativeException();
        }
        if(optionalEvent.isEmpty()){
            throw new EventNotFoundException();
        }
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        DonationEvent donationEvent = new DonationEvent(donationEventDTO.value(), optionalEvent.get(), optionalUser.get());
        return donationEventRepository.save(donationEvent);
    }
}
