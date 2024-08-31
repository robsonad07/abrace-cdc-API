package com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.dto.UpdateDonationActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.exceptions.DonationActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository.DonationActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;

@Service
public class UpdateDonationActionUseCase {
  @Autowired
  private DonationActionRepository donationActionRepository;

  @Autowired 
  private UserRepository userRepository;

  @Autowired 
  private ActionRepository actionRepository;

  public DonationActionEntity execute(UpdateDonationActionDTO updateDonationActionDTO) {
    Optional<DonationActionEntity> donationActionOptional = this.donationActionRepository.findById(updateDonationActionDTO.getId());

    Optional<User> userOptional = this.userRepository.findById(updateDonationActionDTO.getUserId());

    Optional<ActionEntity> actionOptional = this.actionRepository.findById(updateDonationActionDTO.getActionId());

    if (donationActionOptional.isEmpty()) {
      throw new DonationActionNotFoundException();
    }

    if (userOptional.isEmpty()) {
      throw new RuntimeException("user not found");
    }

    if (actionOptional.isEmpty()) {
      throw new RuntimeException("action not found");
    }

    var existingDonationAction = donationActionOptional.get();

    existingDonationAction.setActionEntity(actionOptional.get());
    existingDonationAction.setUser(userOptional.get());
    existingDonationAction.setValue(updateDonationActionDTO.getValue());
    
    this.donationActionRepository.save(existingDonationAction);
  
    return existingDonationAction;
  }

}
