package com.abracecdcAPI.abracecdcAPI.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.user.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.UserRecordDTO;
import com.abracecdcAPI.abracecdcAPI.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

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
