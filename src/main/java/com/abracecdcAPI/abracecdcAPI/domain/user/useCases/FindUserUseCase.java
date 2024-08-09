package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindUserUseCase {
    @Autowired
    private UserRepository repository;

    public User execute(UUID id){
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }
        return user.get();
    }

}
