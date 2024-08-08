package com.abracecdcAPI.abracecdcAPI.domain.user.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.user.dto.AuthenticationDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.dto.LoginResponseDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.UserRole;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.LoginUserUseCase;
import com.abracecdcAPI.abracecdcAPI.infra.security.TokenService;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    @Autowired
    LoginUserUseCase loginUserUseCase;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        var token = loginUserUseCase.execute(new UsernamePasswordAuthenticationToken(data.email(), data.password()));

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, data.phone(), data.role());
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/register-user")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, data.phone(), UserRole.USER);
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }


}
