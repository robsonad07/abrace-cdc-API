package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;

@Service
public class ListAllActionsByFilterUseCase {
  
  @Autowired
  private ActionRepository actionRepository;

  public List<ActionEntity> execute(String actionTitle) {
    var actions = this.actionRepository.findByTitle(actionTitle);
    
    return actions;
  }
}
