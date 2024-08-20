package com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateRegisterUseCase {
    @Autowired
    RegisterRepository registerRepository;
    @Autowired
    private EventRepository eventRepository;

    public Register execute(UUID id, RegisterDTO registerDTO){
        Optional<Register> register = registerRepository.findById(id);
        Optional<Event> optionalEvent = eventRepository.findById(registerDTO.event_id());

        if(register.isEmpty()) throw new RegisterNotFoundException();

        if(optionalEvent.isEmpty()){
            throw new EventNotFoundException();
        }

        Register newRegister = new Register(id, registerDTO.urlImage(), registerDTO.description(), optionalEvent.get());
        registerRepository.save(newRegister);
        return newRegister;
    }
}
