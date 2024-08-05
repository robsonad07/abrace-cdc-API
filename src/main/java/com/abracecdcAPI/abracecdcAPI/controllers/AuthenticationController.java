package com.abracecdcAPI.abracecdcAPI.controllers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.abracecdcAPI.abracecdcAPI.domain.user.*;
import com.abracecdcAPI.abracecdcAPI.infra.security.TokenService;
import com.abracecdcAPI.abracecdcAPI.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/auth/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, data.phone(), data.role());
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/register-user")
    public ResponseEntity registerUser(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, data.phone(), UserRole.USER);
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") UUID id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserRecordDTO userRecordDTO){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userRecordDTO.password());
        User modelUser = new User(id ,userRecordDTO.name(), userRecordDTO.email(), encryptedPassword, userRecordDTO.phone(), userRecordDTO.role());
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(modelUser));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        repository.delete(user.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }


}
