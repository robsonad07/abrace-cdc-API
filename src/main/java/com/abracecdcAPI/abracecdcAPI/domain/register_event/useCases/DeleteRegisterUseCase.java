package com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteRegisterUseCase {
    @Autowired
    RegisterRepository registerRepository;

    public String execute(UUID id){
        Optional<Register> register = registerRepository.findById(id);

        if(register.isEmpty()) throw new RegisterNotFoundException();

        registerRepository.deleteById(id);
        return "Register deleted successfully.";
    }
}