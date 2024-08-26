package com.abracecdcAPI.abracecdcAPI.domain.organizer.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
public class DeleteOrganizerUseCaseTest {

  @Mock
  private OrganizerRepository organizerRepository;

  @InjectMocks
  private DeleteOrganizerUseCase deleteOrganizerUseCase;

  @Test
  @DisplayName("Should be able to delete an organizer")
  public void shoud_be_to_delete_an_organizer() {
    var organizerId = UUID.randomUUID();

    var organizer = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("9999-9999")
        .name("organier-test")
        .email("organizer@test.com")
        .build();

    when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(organizer));

    this.deleteOrganizerUseCase.execute(organizerId);

    verify(organizerRepository).delete(organizer);
  }

  @Test
  @DisplayName("Should not able to delete an organizer that does not exist")
  public void should_not_be_able_to_delete_an_organizer_that_does_not_exist() {
    var organizerId = UUID.randomUUID();

    when(organizerRepository.findById(organizerId)).thenReturn(Optional.empty());

    assertThrows(OrganizerNotFoundException.class, () -> {
      deleteOrganizerUseCase.execute(organizerId);
    });

    verify(organizerRepository, never()).delete(any());
  }
}
