package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;

@Service
public class CreateOrganizerUseCase {
  
  @Autowired  
  private OrganizerRepository organizerRepository;

  public OrganizerEntity execute(OrganizerEntity organizerEntity) {
    return this.organizerRepository.save(organizerEntity);
  }
}
