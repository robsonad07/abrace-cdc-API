package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllUsersUseCase {
    @Autowired
    private UserRepository repository;

    public List<User> execute(){
        return  repository.findAll();
    }
}
