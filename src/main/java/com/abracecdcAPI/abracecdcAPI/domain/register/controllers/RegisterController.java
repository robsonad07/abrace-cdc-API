package com.abracecdcAPI.abracecdcAPI.domain.register.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.register.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register.useCases.CreateRegisterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.register.useCases.GetAllRegisterUserCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegisterController {
    @Autowired
    CreateRegisterUseCase createRegisterUseCase;
    @Autowired
    GetAllRegisterUserCase getAllRegisterUserCase;

    @PostMapping("/register")
    public ResponseEntity<Object> createRegister(@RequestBody @Valid RegisterDTO registerDTO){
        try {
            Register register = createRegisterUseCase.execute(registerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(register);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/registers")
    public ResponseEntity<Object> getAllRegister(){
        try {
            List<Register> registers = getAllRegisterUserCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(registers);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
