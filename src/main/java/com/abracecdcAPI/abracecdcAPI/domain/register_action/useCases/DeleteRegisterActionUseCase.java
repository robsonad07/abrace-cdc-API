package com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.exceptions.RegisterActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.repository.RegisterActionRepository;

@Service
public class DeleteRegisterActionUseCase {
  
  @Autowired
  private RegisterActionRepository registerActionRepository;

  public void execute(UUID registerActionId) {
    Optional<RegisterActionEntity> registerActionOptional = this.registerActionRepository
        .findById(registerActionId);

    if (registerActionOptional.isEmpty()) {
      throw new RegisterActionNotFoundException();
    }

    var existingRegisterAction = registerActionOptional.get();

    this.registerActionRepository.delete(existingRegisterAction);
  }
}
