package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CellphoneAlreadyExistsException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EmailAlreadyExistsException;

@Service
public class CreateOrganizerUseCase {
  
  @Autowired  
  private OrganizerRepository organizerRepository;

  public OrganizerEntity execute(OrganizerEntity organizerEntity) {

    var ornanizerWithSameEmail = this.organizerRepository.findByEmail(organizerEntity.getEmail());

    ornanizerWithSameEmail.ifPresent(organizer -> {
      throw new EmailAlreadyExistsException();
    });

    var ornanizerWithSameCellphone = this.organizerRepository.findByCellphone(organizerEntity.getCellphone());

    ornanizerWithSameCellphone.ifPresent(organizer -> {
      throw new CellphoneAlreadyExistsException();
    });


    return this.organizerRepository.save(organizerEntity);
  }
}
