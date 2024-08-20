package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@Service
public class DeleteOrganizerUseCase {
  
  @Autowired  
  private OrganizerRepository organizerRepository;

  public void execute(UUID organizerId) {
    Optional<OrganizerEntity> organizerOptional = 
      this.organizerRepository.findById(organizerId);

      if(organizerOptional.isEmpty()) {
        throw new OrganizerNotFoundException();
      }

      var existingOrganizer = organizerOptional.get();

      this.organizerRepository.delete(existingOrganizer);
  }
}
