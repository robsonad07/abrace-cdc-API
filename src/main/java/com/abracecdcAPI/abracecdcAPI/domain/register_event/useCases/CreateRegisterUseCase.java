package com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateRegisterUseCase {
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private EventRepository eventRepository;

    public Register execute(RegisterDTO registerDTO){
        Optional<Register> optionalRegister = registerRepository.findByUrlImage(registerDTO.urlImage());
        Optional<Event> optionalEvent = eventRepository.findById(registerDTO.event_id());

        if(optionalRegister.isPresent()){
            throw new RegisterAlreadyExistsException();
        }
        if(optionalEvent.isEmpty()){
            throw new EventNotFoundException();
        }

        Register newRegister = new Register(registerDTO.urlImage(), registerDTO.description(), optionalEvent.get());

        registerRepository.save(newRegister);
        return newRegister;
    }
}
