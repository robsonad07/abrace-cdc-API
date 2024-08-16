package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@Service
public class GetOrganizerByIdUseCase {

  @Autowired
  private OrganizerRepository organizerRepository;


  public OrganizerEntity execute(UUID idOrganizer) {
    Optional<OrganizerEntity> organizerOptional = organizerRepository.findById(idOrganizer);
  
    if(organizerOptional.isEmpty()) {
      throw new OrganizerNotFoundException();
    }

    return organizerOptional.get();

  }
}
