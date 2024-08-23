package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@Service
public class GetActionByIdUseCase {
  
  @Autowired
  private ActionRepository actionRepository;

  public ActionEntity execute(UUID idAction) {
    Optional<ActionEntity> actionOptional = this.actionRepository.findById(idAction);

    if(actionOptional.isEmpty()) {
      throw new ActionNotFoundException();
    }

    var existingAction = actionOptional.get();
    return existingAction;
  }
}
