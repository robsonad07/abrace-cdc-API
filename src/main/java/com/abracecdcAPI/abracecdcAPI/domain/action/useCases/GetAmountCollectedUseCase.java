package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@Service
public class GetAmountCollectedUseCase {
    
  @Autowired
  private ActionRepository actionRepository;
  
  public float execute(UUID actionId) {
    
    Optional<ActionEntity> actionOptional = this.actionRepository.findById(actionId);

    if (actionOptional.isEmpty()) {
      throw new ActionNotFoundException();
    }

    var action = actionOptional.get();

    float totalAmountCollected = action.getDonationsAction()
        .stream()
        .map(DonationActionEntity::getValue) // Obter o valor de cada doação
        .reduce(0, Integer::sum); // Somar os valores de todas as doações

    return totalAmountCollected;

  }
}
