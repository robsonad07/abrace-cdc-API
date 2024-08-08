package com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.entity.OrganizerEventEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.repository.OrganizerEventRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerEventNotFoundException;

@Service
public class DeleteOrganizerEventUseCase {
  
  @Autowired  
  private OrganizerEventRepository organizerEventRepository;

  public void execute(UUID organizerEventId) {
    Optional<OrganizerEventEntity> organizerOptional = 
      this.organizerEventRepository.findById(organizerEventId);

      if(organizerOptional.isEmpty()) {
        throw new OrganizerEventNotFoundException();
      }

      var existingOrganizerEvent = organizerOptional.get();

      this.organizerEventRepository.delete(existingOrganizerEvent);
  }
}
