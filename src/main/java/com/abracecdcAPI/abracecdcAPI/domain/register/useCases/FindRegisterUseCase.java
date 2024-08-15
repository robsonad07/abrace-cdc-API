package com.abracecdcAPI.abracecdcAPI.domain.register.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindRegisterUseCase {
    @Autowired
    private RegisterRepository registerRepository;

    public Register execute(UUID id){
        Optional<Register> register = registerRepository.findById(id);
        if(register.isEmpty()) throw new RegisterNotFoundException();
        return register.get();
    }
}
