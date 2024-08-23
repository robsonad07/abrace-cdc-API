package com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.dto.CreateRegisterActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.repository.RegisterActionRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@Service
public class CreateRegisterActionUseCase {

  @Autowired
  private RegisterActionRepository registerActionRepository;

  @Autowired
  private ActionRepository actionRepository;

  public RegisterActionEntity execute(CreateRegisterActionDTO createRegisterActionDTO) {

    Optional<ActionEntity> actionOptional = this.actionRepository.findById(createRegisterActionDTO.getActionId());

    if (actionOptional.isEmpty()) {
      throw new ActionNotFoundException();
    }

    var registerActionEntity = RegisterActionEntity.builder()
        .urlImage(createRegisterActionDTO.getUrlImage())
        .description(createRegisterActionDTO.getDescription())
        .actionEntity(actionOptional.get())
        .build();

    var registerCreated = this.registerActionRepository.save(registerActionEntity);

    return registerCreated;
  }

}
