package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.user.dto.UserRecordDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateAdminUseCase {
    @Autowired
    private UserRepository repository;

    public User execute(UUID id, UserRecordDTO userRecordDTO) {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userRecordDTO.password());
        User modelUser = new User(id ,userRecordDTO.name(), userRecordDTO.email(), encryptedPassword, userRecordDTO.phone(), userRecordDTO.role(),
                user.get().getDonationEvents());
        repository.save(modelUser);
        return modelUser;
    }
}
