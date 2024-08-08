package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@Service
public class UpdateOrganizerUseCase {
  
  @Autowired  
  private OrganizerRepository organizerRepository;

  public OrganizerEntity execute(OrganizerEntity organizerEntityToUpdate) {
    Optional<OrganizerEntity> organizerOptional = 
      this.organizerRepository.findById(organizerEntityToUpdate.getId());

    if (organizerOptional.isEmpty()) {
      throw new OrganizerNotFoundException();
    }

    var existingOrganizer = organizerOptional.get();

    existingOrganizer.setName(organizerEntityToUpdate.getName());  
    existingOrganizer.setCellphone(organizerEntityToUpdate.getCellphone());  
    existingOrganizer.setEmail(organizerEntityToUpdate.getEmail()); 
    
    var result = organizerRepository.save(existingOrganizer);

    return result;

  }
}
