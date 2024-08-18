package com.abracecdcAPI.abracecdcAPI.domain.event.UseCases;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllEventUseCase {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> execute(){
        return eventRepository.findAll();
    }
}
