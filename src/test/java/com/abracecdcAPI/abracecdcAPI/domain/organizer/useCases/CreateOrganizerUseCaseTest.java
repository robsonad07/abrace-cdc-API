package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.abracecdcAPI.abracecdcAPI.exceptions.CellphoneAlreadyExistsException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EmailAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class CreateOrganizerUseCaseTest {

  @InjectMocks
  private CreateOrganizerUseCase createOrganizerUseCase;

  @Mock
  private OrganizerRepository organizerRepository;

  @Test
  @DisplayName("Should be able to create an organizer")
  public void should_be_able_to_create_an_organizer() {
    var organizerToCreate = OrganizerEntity.builder()
        .id(UUID.randomUUID())
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    when(organizerRepository.save(any(OrganizerEntity.class))).thenReturn(organizerToCreate);

    var organizerCreated = createOrganizerUseCase.execute(organizerToCreate);

    assertEquals(organizerToCreate, organizerCreated);

  }

  @Test
  @DisplayName("Should not be able to create an organizer with same email")
  public void should_not_be_able_to_create_an_organizer_with_same_email() {

    var existingOrganizer = OrganizerEntity.builder()
        .id(UUID.randomUUID())
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    var newOrganizer = OrganizerEntity.builder()
        .id(UUID.randomUUID())
        .cellphone("8888-8888")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    when(organizerRepository.findByEmail("organizer@test.com")).thenReturn(Optional.of(existingOrganizer));

    assertThrows(EmailAlreadyExistsException.class, () -> {
      createOrganizerUseCase.execute(newOrganizer);
    });

  }

  @Test
  @DisplayName("Should not be able to create an organizer with same cellphone")
  public void should_not_be_able_to_create_an_organizer_with_same_cellphone() {

    var existingOrganizer = OrganizerEntity.builder()
        .id(UUID.randomUUID())
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    var newOrganizer = OrganizerEntity.builder()
        .id(UUID.randomUUID())
        .cellphone("9999-9999")
        .name("new-organier-test")
        .email("new-organizer@test.com")
        .build();

    when(organizerRepository.findByCellphone("9999-9999")).thenReturn(Optional.of(existingOrganizer));

    assertThrows(CellphoneAlreadyExistsException.class, () -> {
      createOrganizerUseCase.execute(newOrganizer);
    });

  }

}