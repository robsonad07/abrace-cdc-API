package com.abracecdcAPI.abracecdcAPI.domain.register.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.register.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register.useCases.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class RegisterController {
    @Autowired
    CreateRegisterUseCase createRegisterUseCase;
    @Autowired
    GetAllRegisterUserCase getAllRegisterUserCase;
    @Autowired
    FindRegisterUseCase findRegisterUseCase;
    @Autowired
    UpdateRegisterUseCase updateRegisterUseCase;
    @Autowired
    DeleteRegisterUseCase deleteRegisterUseCase;

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

    @GetMapping("/register/{id}")
    public ResponseEntity<Object> findRegister(@PathVariable(value = "id") UUID id){
        try{
            Register register = findRegisterUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(register);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}")
    public ResponseEntity<Object> updateRegister(@PathVariable(value = "id") UUID id, @RequestBody @Valid RegisterDTO registerDTO){
        try {
            Register register = updateRegisterUseCase.execute(id, registerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(register);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/register/{id}")
    public ResponseEntity<Object> deleteRegister(@PathVariable(value = "id") UUID id){
        try{
            String msm = deleteRegisterUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(msm);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
