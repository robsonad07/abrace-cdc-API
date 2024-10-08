package com.abracecdcAPI.abracecdcAPI.domain.event.UseCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;

@Service
public class DeleteEventUseCase {
    @Autowired
    EventRepository eventRepository;

    public String execute(UUID id){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isEmpty()){
            throw new EventNotFoundException();
        }
        eventRepository.deleteById(id);
        return "Event deleted successfully.";
    }


}
