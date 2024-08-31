package com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.dto.UpdateRegisterActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.exceptions.RegisterActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.repository.RegisterActionRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@Service
public class UpdateRegisterActionUseCase {

  @Autowired
  private RegisterActionRepository registerActionRepository;

  @Autowired
  private ActionRepository actionRepository;

  public RegisterActionEntity execute(UpdateRegisterActionDTO updateRegisterActionDTO) {
    Optional<RegisterActionEntity> registerActionOptional = this.registerActionRepository
        .findById(updateRegisterActionDTO.getId());

    if (registerActionOptional.isEmpty()) {
      throw new RegisterActionNotFoundException();
    }

    Optional<ActionEntity> actionEntityOptional = this.actionRepository.findById(updateRegisterActionDTO.getActionId());

    if(actionEntityOptional.isEmpty()) {
      throw new ActionNotFoundException();
    }

    var existingRegisterAction = registerActionOptional.get();

    existingRegisterAction.setActionEntity(actionEntityOptional.get());
    existingRegisterAction.setDescription(updateRegisterActionDTO.getDescription());
    existingRegisterAction.setUrlImage(updateRegisterActionDTO.getUrlImage());

    var registerActionUpdated = this.registerActionRepository.save(existingRegisterAction);

    return registerActionUpdated;
  }
}
