package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;

@ExtendWith(MockitoExtension.class)
public class ListAllOrganizerByFilterUseCaseTest {

  @InjectMocks
  private ListAllOrganizerByFilterUseCase listAllOrganizerByFilterUseCase;

  @Mock
  private OrganizerRepository organizerRepository;

  @Test
  @DisplayName("Should be able to list all organizer by filter")
  public void should_be_able_to_list_all_organizer_by_filter() {

    var firstOrganizer = OrganizerEntity.builder()
        .name("organizer")
        .cellphone("9999-9999")
        .email("first-organizer@teste.com")
        .build();

    var secondOrganizer = OrganizerEntity.builder()
        .name("organizer")
        .cellphone("8888-8888")
        .email("second-organizer@teste.com")
        .build();

    List<OrganizerEntity> organizerList = new ArrayList<>();
    organizerList.add(firstOrganizer);
    organizerList.add(secondOrganizer);

    when(listAllOrganizerByFilterUseCase.execute("organizer")).thenReturn(organizerList);

    var retrivedOrganizerList = this.listAllOrganizerByFilterUseCase.execute("organizer");

    assertEquals(organizerList, retrivedOrganizerList);

  }

  @Test
  @DisplayName("Should be return a empty list")
  public void should_be_return_a_empty_list_when_no_organizer_match_filter() {
    
    List<OrganizerEntity> emptyOrganizerList = new ArrayList<>();
    
    when(listAllOrganizerByFilterUseCase.execute(anyString())).thenReturn(emptyOrganizerList);
    
    var retrivedOrganizerList = this.listAllOrganizerByFilterUseCase.execute("not-exists-organizer");

    assertEquals(emptyOrganizerList, retrivedOrganizerList);
  }
}
