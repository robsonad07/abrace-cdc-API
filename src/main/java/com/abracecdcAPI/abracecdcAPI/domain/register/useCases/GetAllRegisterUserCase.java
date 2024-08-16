package com.abracecdcAPI.abracecdcAPI.domain.register.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllRegisterUserCase {
    @Autowired
    RegisterRepository registerRepository;

    public List<Register> execute(){
        return registerRepository.findAll();
    }
}
