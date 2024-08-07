package com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.entity.OrganizerEventEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.repository.OrganizerEventRepository;

@Service
public class CreateOrganizerEventUseCase {
  
  @Autowired  
  private OrganizerEventRepository organizerEventRepository;

  public OrganizerEventEntity execute(OrganizerEventEntity organizerEventEntity) {
    return organizerEventRepository.save(organizerEventEntity);
  }
}
