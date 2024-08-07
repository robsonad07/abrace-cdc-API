package com.abracecdcAPI.abracecdcAPI.domain.organizer_event.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.entity.OrganizerEventEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer_event.repository.OrganizerEventRepository;

@Service
public class ListAllOrganizerEventByFilterUseCase {
  
  @Autowired
  private OrganizerEventRepository organizerEventRepository;

  public List<OrganizerEventEntity> execute(String name) {
    var organizerEvents = organizerEventRepository.findByName(name);

    return organizerEvents;
  }
}
