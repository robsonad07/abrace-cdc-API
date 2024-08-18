package com.abracecdcAPI.abracecdcAPI.domain.event.UseCases;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindEventUseCase {
    @Autowired
    private EventRepository eventRepository;

    public Event execute(UUID id){
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isEmpty()) throw new EventNotFoundException();
        return optionalEvent.get();
    }
}
