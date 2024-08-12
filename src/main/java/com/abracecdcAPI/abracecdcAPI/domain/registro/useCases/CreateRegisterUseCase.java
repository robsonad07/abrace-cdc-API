package com.abracecdcAPI.abracecdcAPI.domain.registro.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.registro.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.registro.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.registro.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateRegisterUseCase {
    @Autowired
    private RegisterRepository registerRepository;

    public Register execute(RegisterDTO registerDTO){
        Register newRegister = new Register(registerDTO.urlImage(), registerDTO.description());
        Optional<Register> optionalRegister = registerRepository.findByUrlImage(newRegister.getUrlImage());
        if(optionalRegister.isPresent()){
            throw new RegisterAlreadyExistsException();
        }
        registerRepository.save(newRegister);
        return newRegister;
    }
}
