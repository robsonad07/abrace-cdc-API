package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;

@Service
public class ListAllOrganizer {
  
  @Autowired
  private OrganizerRepository organizerRepository;

  public List<OrganizerEntity> execute() {
    return this.organizerRepository.findAll();
  }
}
