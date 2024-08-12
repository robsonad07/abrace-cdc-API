package com.abracecdcAPI.abracecdcAPI.domain.registro.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.registro.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.registro.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.registro.useCases.CreateRegisterUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    CreateRegisterUseCase createRegisterUseCase;

    @PostMapping("/register")
    public ResponseEntity<Object> createRegister(@RequestBody @Valid RegisterDTO registerDTO){
        try {
            Register register = createRegisterUseCase.execute(registerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(register);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
