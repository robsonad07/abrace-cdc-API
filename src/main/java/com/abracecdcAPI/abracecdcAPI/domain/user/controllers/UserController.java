package com.abracecdcAPI.abracecdcAPI.domain.user.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.FindUserUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.dto.UserRecordDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.ListAllUsersUseCase;
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
    @Autowired
    private ListAllUsersUseCase listAllUsersUseCase;
    @Autowired
    private FindUserUseCase findUserUseCase;

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(){
        try {
            List<User> users = listAllUsersUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") UUID id){
        try {
            User user = findUserUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
