package com.abracecdcAPI.abracecdcAPI.domain.register_event.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.register_event.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.CreateRegisterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.DeleteRegisterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.FindRegisterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.GetAllRegisterUserCase;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.UpdateRegisterUseCase;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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
