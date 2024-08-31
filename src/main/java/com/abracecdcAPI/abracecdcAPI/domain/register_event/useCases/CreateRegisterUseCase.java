package com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterAlreadyExistsException;

@Service
public class CreateRegisterUseCase {
    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private EventRepository eventRepository;

    public Register execute(RegisterDTO registerDTO) {
        Optional<Register> optionalRegister = registerRepository.findByUrlImage(registerDTO.urlImage());
        Optional<Event> optionalEvent = eventRepository.findById(registerDTO.event_id());

        if (optionalRegister.isPresent()) {
            throw new RegisterAlreadyExistsException(registerDTO.urlImage());
        }
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(registerDTO.event_id());
        }

        Register newRegister = new Register(registerDTO.urlImage(), registerDTO.description(), optionalEvent.get());

        return registerRepository.save(newRegister);
    }
}
