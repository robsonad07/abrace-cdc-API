package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.user.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.UserRole;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    @Autowired
    private UserRepository repository;

    public User execute(RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return null;

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, data.phone(), UserRole.USER);
        
        return repository.save(newUser);
    }
}
