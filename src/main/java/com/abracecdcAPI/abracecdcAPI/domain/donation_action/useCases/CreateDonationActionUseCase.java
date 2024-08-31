package com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.dto.CreateDonationActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository.DonationActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ValueOfDonationActionIsNegativeException;

@Service
public class CreateDonationActionUseCase {
  @Autowired
  private DonationActionRepository donationActionRepository;

  @Autowired 
  private UserRepository userRepository;

  @Autowired 
  private ActionRepository actionRepository;

  public DonationActionEntity execute(CreateDonationActionDTO createDonationActionDTO) {

    Optional<User> userOptional = this.userRepository.findById(createDonationActionDTO.getUserId());

    Optional<ActionEntity> actionOptional = this.actionRepository.findById(createDonationActionDTO.getActionId());

    if(createDonationActionDTO.getValue() < 0) {
      throw new ValueOfDonationActionIsNegativeException();
    }

    if(userOptional.isEmpty()) {
      throw new RuntimeException("user not found");
    }

    if(actionOptional.isEmpty()) {
      throw new RuntimeException("action not found");
    }
    
    var donationActionEntity = DonationActionEntity.builder()
        .user(userOptional.get())
        .actionEntity(actionOptional.get())
        .value(createDonationActionDTO.getValue())
        .build();

    var donationActionCreated = this.donationActionRepository.save(donationActionEntity);

    return donationActionCreated;

  }
}
