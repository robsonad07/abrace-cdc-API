package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GetOrganizerByIdUseCaseTest {

  @Mock
  private OrganizerRepository organizerRepository;

  @InjectMocks
  private GetOrganizerByIdUseCase getOrganizerByIdUseCase;

  @Test
  @DisplayName("It should be able to get an organizer by id")
  public void it_should_be_able_to_get_an_organizer_by_id() {
    var organizerId = UUID.randomUUID();

    var expectedOrganizer = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(expectedOrganizer));

    var retrievedOrganizer = getOrganizerByIdUseCase.execute(organizerId);

    assertEquals(expectedOrganizer, retrievedOrganizer);
  }

  @Test
  @DisplayName("It shoud not be able to get an organizer that does not exist")
  public void it_should_not_be_able_to_get_an_organizer_that_does_not_exist() {
    var organizerId = UUID.randomUUID();

    when(organizerRepository.findById(organizerId)).thenReturn(Optional.empty());

    assertThrows(OrganizerNotFoundException.class, () -> {
      getOrganizerByIdUseCase.execute(organizerId);
    });
  }
}
