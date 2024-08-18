package com.abracecdcAPI.abracecdcAPI.domain.event.UseCases;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.dto.EventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateEventUseCase {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrganizerRepository organizerRepository;

    public Event execute(UUID id, EventDTO eventDTO){
        Optional<Event> optionalEvent = eventRepository.findById(id);
        Optional<Address> optionalAddress = addressRepository.findById(eventDTO.address_id());
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(eventDTO.category_id());
        Optional<OrganizerEntity> optionalOrganizer = organizerRepository.findById(eventDTO.organizer_id());

        if(optionalEvent.isEmpty()) throw new EventNotFoundException();

        if (optionalAddress.isEmpty()) {
            throw new AddressNotFoundException();
        }
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        if(optionalOrganizer.isEmpty()){
            throw new OrganizerNotFoundException();
        }

        Address address = optionalAddress.get();
        CategoryEntity category = optionalCategory.get();
        OrganizerEntity organizer = optionalOrganizer.get();

        Event event = new Event(id, eventDTO.title(), eventDTO.caption(), eventDTO.description(), eventDTO.dateTime(),
                category, organizer, address, optionalEvent.get().getRegisters());

        eventRepository.save(event);

        return event;
    }
}
