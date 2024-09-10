package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;

@Service
public class GetUserByEmailUseCase {
  @Autowired
  private UserRepository userRepository;

  public User execute(String email) {
    var user = this.userRepository.findByEmail(email);
    return user;
  }
}
