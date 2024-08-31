package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CellphoneAlreadyExistsException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EmailAlreadyExistsException;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateOrganizerUseCaseTest {

  @InjectMocks
  private UpdateOrganizerUseCase updateOrganizerUseCase;

  @Mock
  private OrganizerRepository organizerRepository;

  @Test
  public void should_be_able_to_update_an_organizer() {

    var organizerId = UUID.randomUUID();

    var existingOrganizer = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    var updatedOrgazier = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("8888-9999")
        .name("new-name-organizer")
        .email("new-organizer@test.com")
        .build();

    when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(existingOrganizer));
    when(organizerRepository.save(any(OrganizerEntity.class))).thenReturn(updatedOrgazier);

    var retrievedOrganizer = this.updateOrganizerUseCase.execute(updatedOrgazier);

    ArgumentCaptor<OrganizerEntity> categoryArgumentCaptor = ArgumentCaptor.forClass(OrganizerEntity.class);
    verify(organizerRepository).save(categoryArgumentCaptor.capture());
    OrganizerEntity capturedOrganizer = categoryArgumentCaptor.getValue();

    assertEquals(updatedOrgazier, retrievedOrganizer);

    assertEquals(capturedOrganizer.getName(), "new-name-organizer");
    assertEquals(capturedOrganizer.getEmail(), "new-organizer@test.com");
    assertEquals(capturedOrganizer.getCellphone(), "8888-9999");

    verify(organizerRepository, times(1)).findById(organizerId);
    verify(organizerRepository, times(1)).save(any(OrganizerEntity.class));
  }

  @Test
  @DisplayName("Should not be able to update an organizer that does not exists")
  public void should_not_be_able_to_update_an_organizer_that_does_not_exists() {
    var organizerId = UUID.randomUUID();

    var notExistingOrganizer = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    when(organizerRepository.findById(organizerId)).thenReturn(Optional.empty());

    assertThrows(OrganizerNotFoundException.class, () -> {
      updateOrganizerUseCase.execute(notExistingOrganizer);
    });

  }

  @Test
  @DisplayName("Should not be able to update an organizer that email alredy exists")
  public void should_not_be_able_to_update_an_organizer_that_email_alredy_exists() {
    var organizerId = UUID.randomUUID();

    var organizerWithEmail = OrganizerEntity.builder()
        .id(UUID.randomUUID())
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    var updatedOrganizer = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("8888-8888")
        .name("new-name-organizer")
        .email("organizer@test.com")
        .build();

    when(organizerRepository.findByEmail("organizer@test.com")).thenReturn(Optional.of(organizerWithEmail));

    assertThrows(EmailAlreadyExistsException.class, () -> {
      updateOrganizerUseCase.execute(updatedOrganizer);
    });

  }

  @Test
  @DisplayName("Should not be able to update an organizer that cellphone alredy exists")
  public void should_not_be_able_to_update_an_organizer_that_cellphone_alredy_exists() {
    var organizerId = UUID.randomUUID();

    var organizerWithCellphone = OrganizerEntity.builder()
        .id(UUID.randomUUID())
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    var updatedOrganizer = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("9999-9999")
        .name("new-name-organizer")
        .email("updated-organizer@test.com")
        .build();

    when(organizerRepository.findByCellphone("9999-9999")).thenReturn(Optional.of(organizerWithCellphone));

    assertThrows(CellphoneAlreadyExistsException.class, () -> {
      updateOrganizerUseCase.execute(updatedOrganizer);
    });

  }
}
