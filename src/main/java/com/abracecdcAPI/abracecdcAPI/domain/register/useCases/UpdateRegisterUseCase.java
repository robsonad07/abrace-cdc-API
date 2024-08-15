package com.abracecdcAPI.abracecdcAPI.domain.register.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.register.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateRegisterUseCase {
    @Autowired
    RegisterRepository registerRepository;

    public Register execute(UUID id, RegisterDTO registerDTO){
        Optional<Register> register = registerRepository.findById(id);

        if(register.isEmpty()) throw new RegisterNotFoundException();

        Register newRegister = new Register(id, registerDTO.urlImage(), registerDTO.description());
        registerRepository.save(newRegister);
        return newRegister;
    }
}
