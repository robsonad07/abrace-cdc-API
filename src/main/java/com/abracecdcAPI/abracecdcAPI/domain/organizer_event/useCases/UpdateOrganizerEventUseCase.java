package com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.entity.OrganizerEventEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.repository.OrganizerEventRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerEventNotFoundException;

@Service
public class UpdateOrganizerEventUseCase {
  
  @Autowired  
  private OrganizerEventRepository organizerEventRepository;

  public OrganizerEventEntity execute(OrganizerEventEntity organizerEventEntityToUpdate) {
    Optional<OrganizerEventEntity> organizerOptional = 
      this.organizerEventRepository.findById(organizerEventEntityToUpdate.getId());

    if (organizerOptional.isEmpty()) {
      throw new OrganizerEventNotFoundException();
    }

    var existingOrganizerEvent = organizerOptional.get();

    existingOrganizerEvent.setName(organizerEventEntityToUpdate.getName());  
    existingOrganizerEvent.setCellphone(organizerEventEntityToUpdate.getCellphone());  
    existingOrganizerEvent.setEmail(organizerEventEntityToUpdate.getEmail()); 
    
    var result = organizerEventRepository.save(existingOrganizerEvent);

    return result;

  }
}
