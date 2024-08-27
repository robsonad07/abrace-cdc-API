package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GetActionByIdUseCaseTest {
  
  @InjectMocks
  private GetActionByIdUseCase getActionByIdUseCase;

  @Mock
  private ActionRepository actionRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private OrganizerRepository organizerRepository;

  @Mock
  private AddressRepository addressRepository;

  @Test
  @DisplayName("Should be able to get an action")
  public void should_be_able_to_get_an_action() {

    var actionId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var organizerId = UUID.randomUUID();
    var addressId = UUID.randomUUID();

    var category = CategoryEntity.builder()
        .id(categoryId)
        .name("teste")
        .description("teste")
        .build();

    var organizer = OrganizerEntity.builder()
        .id(organizerId)
        .cellphone("9999-9999")
        .name("organizer-test")
        .email("organizer@test.com")
        .build();

    var address = Address.builder()
        .id(addressId)
        .city("teste")
        .cep("62960-000")
        .road("teste")
        .number(123)
        .complement("teste")
        .build();

    var actionToDelete = ActionEntity.builder()
        .id(actionId)
        .title("title-test")
        .subtitle("test-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .categoryEntity(category)
        .organizerEntity(organizer)
        .addressEntity(address)
        .build();

    when(actionRepository.findById(actionId)).thenReturn(Optional.of(actionToDelete));

    getActionByIdUseCase.execute(actionId);

    ArgumentCaptor<UUID> actionIdArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    verify(actionRepository).findById(actionIdArgumentCaptor.capture());
    var capturedActionId = actionIdArgumentCaptor.getValue();

    assertEquals(actionId, capturedActionId);
    
    verify(actionRepository, times(1)).findById(actionId);
  }

  @Test
  @DisplayName("Should not be able to get a non existent action")
  public void should_not_be_able_to_get_a_non_existent_action() {

    var actionId = UUID.randomUUID();

    when(actionRepository.findById(actionId)).thenReturn(Optional.empty());


    assertThrows(ActionNotFoundException.class, () -> {
      getActionByIdUseCase.execute(actionId);
    });

    verify(actionRepository, times(1)).findById(actionId);

  }
}
