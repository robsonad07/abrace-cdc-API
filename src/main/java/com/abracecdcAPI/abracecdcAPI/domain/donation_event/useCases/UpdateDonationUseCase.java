package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto.DonationEventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.DonationEventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateDonationUseCase {
    @Autowired
    private DonationEventRepository donationEventRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    public DonationEvent execute(UUID id, DonationEventDTO donationEventDTO){
        Optional<DonationEvent> optionalDonationEvent = donationEventRepository.findById(id);
        Optional<Event> optionalEvent = eventRepository.findById(donationEventDTO.event_id());
        Optional<User> optionalUser = userRepository.findById((donationEventDTO.user_id()));

        if(optionalDonationEvent.isEmpty()){
            throw new DonationEventNotFoundException();
        }
        if(optionalEvent.isEmpty()){
            throw new EventNotFoundException();
        }
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }

        DonationEvent donationEvent = new DonationEvent(id, donationEventDTO.value(), optionalEvent.get(), optionalUser.get());
        donationEventRepository.save(donationEvent);
        return donationEvent;
    }
}
