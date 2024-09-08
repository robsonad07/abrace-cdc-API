package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;

@Service
public class ListAllActionsUseCase {
  
  @Autowired
  private ActionRepository actionRepository;

  public List<ActionEntity> execute() {
    return this.actionRepository.findAll();
  }
}
