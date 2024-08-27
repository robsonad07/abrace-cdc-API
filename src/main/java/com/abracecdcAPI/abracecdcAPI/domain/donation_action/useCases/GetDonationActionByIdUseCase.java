package com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.exceptions.DonationActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository.DonationActionRepository;

@Service
public class GetDonationActionByIdUseCase {
  @Autowired
  private DonationActionRepository donationActionRepository;

  public DonationActionEntity execute(UUID DonationActionId) {
    Optional<DonationActionEntity> donationActionOptional = this.donationActionRepository.findById(DonationActionId);

    if(donationActionOptional.isEmpty()) {
      throw new DonationActionNotFoundException();
    }

    var existingDonationAction = donationActionOptional.get();

    return existingDonationAction;

  }
}
