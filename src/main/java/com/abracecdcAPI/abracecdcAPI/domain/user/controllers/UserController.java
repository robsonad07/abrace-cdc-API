package com.abracecdcAPI.abracecdcAPI.domain.user.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.DeleteUserUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.FindUserUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.dto.UserRecordDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.ListAllUsersUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.user.useCases.UpdateAdminUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private ListAllUsersUseCase listAllUsersUseCase;
    @Autowired
    private FindUserUseCase findUserUseCase;
    @Autowired
    private UpdateAdminUseCase updateAdminUseCase;
    @Autowired
    private DeleteUserUseCase deleteUserUseCase;

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
        try {
            User modelUser = updateAdminUseCase.execute(id, userRecordDTO);
            return ResponseEntity.status(HttpStatus.OK).body(modelUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id){
        try {
            String response = deleteUserUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
