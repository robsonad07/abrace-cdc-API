package com.abracecdcAPI.abracecdcAPI.domain.user.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.user.dto.AuthenticationDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.dto.LoginResponseDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.LoginUserUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.RegisterAdminUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    @Autowired
    LoginUserUseCase loginUserUseCase;
    @Autowired
    RegisterAdminUseCase registerAdminUseCase;
    @Autowired
    RegisterUserUseCase registerUserUseCase;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        try {
            var token = loginUserUseCase.execute(new UsernamePasswordAuthenticationToken(data.email(), data.password()));
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data){
        try{
            User newUser = registerAdminUseCase.execute(data);
            if(newUser == null){
                return ResponseEntity.badRequest().body("User already registered");
            }
            return ResponseEntity.ok().body(newUser);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/auth/register-user")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterDTO data){
        try {
            User newUser = registerUserUseCase.execute(data);
            if(newUser == null){
                return ResponseEntity.badRequest().body("User already registered");
            }
            return ResponseEntity.ok().body(newUser);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
