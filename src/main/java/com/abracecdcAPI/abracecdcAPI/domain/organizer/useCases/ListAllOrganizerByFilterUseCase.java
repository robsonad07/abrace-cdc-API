package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;

@Service
public class ListAllOrganizerByFilterUseCase {
  
  @Autowired
  private OrganizerRepository organizerRepository;

  public List<OrganizerEntity> execute(String name) {
    var organizers = this.organizerRepository.findByName(name);

    return organizers;
  }
}
