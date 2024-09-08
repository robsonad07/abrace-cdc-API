package com.abracecdcAPI.abracecdcAPI.domain.event.UseCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;

@Service
public class GetLastEventsUseCase {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> execute(int page, int size) {
        Page<Event> events = eventRepository.findAllByOrderByDateTimeDesc(PageRequest.of(page, size));
        return events.getContent();
    }
}
