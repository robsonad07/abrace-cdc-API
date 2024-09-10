package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.abracecdcAPI.abracecdcAPI.domain.action.dto.CreateActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.exceptions.ActionAlredyExistsException;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CreateActionUseCaseTest {

  @InjectMocks
  private CreateActionUseCase createActionUseCase;

  @Mock
  private ActionRepository actionRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private OrganizerRepository organizerRepository;

  @Test
  @DisplayName("Should be able to create an action")
  public void should_be_able_to_create_an_actionA() {
    var actionId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var organizerId = UUID.randomUUID();

    CreateActionDTO createActionDTO = CreateActionDTO.builder()
        .actionId(actionId)
        .categoryId(categoryId)
        .organizerId(organizerId)
        .title("title-test")
        .subtitle("test-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .build();

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

    var actionToCreate = ActionEntity.builder()
        .id(actionId)
        .title("title-test")
        .subtitle("test-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .categoryEntity(category)
        .organizerEntity(organizer)
        .build();

    when(actionRepository.save(any(ActionEntity.class))).thenReturn(actionToCreate);
    when(actionRepository.findByTitleAndSubtitle(createActionDTO.getTitle(), createActionDTO.getSubtitle()))
        .thenReturn(Optional.empty());
    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
    when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(organizer));

    var actionCreated = createActionUseCase.execute(createActionDTO);

    ArgumentCaptor<ActionEntity> actionArgumentCaptor = ArgumentCaptor.forClass(ActionEntity.class);
    verify(actionRepository).save(actionArgumentCaptor.capture());
    ActionEntity captureAction = actionArgumentCaptor.getValue();

    assertEquals(actionToCreate, actionCreated);

    assertEquals(actionCreated.getTitle(), captureAction.getTitle());
    assertEquals(actionCreated.getSubtitle(), captureAction.getSubtitle());
    assertEquals(actionCreated.getDescription(), captureAction.getDescription());
    assertEquals(actionCreated.getDateTime(), captureAction.getDateTime());
    assertEquals(actionCreated.getCategoryEntity(), captureAction.getCategoryEntity());
    assertEquals(actionCreated.getOrganizerEntity(), captureAction.getOrganizerEntity());

    verify(actionRepository, times(1)).findByTitleAndSubtitle(createActionDTO.getTitle(),
        createActionDTO.getSubtitle());
    verify(categoryRepository, times(1)).findById(categoryId);
    verify(organizerRepository, times(1)).findById(organizerId);
    verify(actionRepository, times(1)).save(any(ActionEntity.class));

  }

  @Test
  @DisplayName("Should be able to create an action with same title and subtitle")
  public void should_not_be_able_to_create_an_action_with_same_title_and_subtitle() {
    var actionId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var organizerId = UUID.randomUUID();

    CreateActionDTO createActionDTO = CreateActionDTO.builder()
        .actionId(actionId)
        .categoryId(categoryId)
        .organizerId(organizerId)
        .title("title-test")
        .subtitle("test-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .build();

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

    var existingAction = ActionEntity.builder()
        .id(actionId)
        .title("title-test")
        .subtitle("test-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .categoryEntity(category)
        .organizerEntity(organizer)
        .build();

    when(actionRepository.findByTitleAndSubtitle(createActionDTO.getTitle(), createActionDTO.getSubtitle()))
        .thenReturn(Optional.of(existingAction));

    assertThrows(ActionAlredyExistsException.class, () -> {
      this.createActionUseCase.execute(createActionDTO);
    });

    verify(actionRepository, times(1)).findByTitleAndSubtitle(createActionDTO.getTitle(),
        createActionDTO.getSubtitle());

  }

  @Test
  @DisplayName("Should be able to create an action with a non existent organizer")
  public void should_not_be_able_to_create_an_action_with_a_non_existent_organizer() {
    var categoryId = UUID.randomUUID();
    var organizerId = UUID.randomUUID();

    CreateActionDTO createActionDTO = CreateActionDTO.builder()
        .categoryId(categoryId)
        .organizerId(organizerId)
        .title("title-test")
        .subtitle("test-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .build();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

    assertThrows(CategoryNotFoundException.class, () -> {
      this.createActionUseCase.execute(createActionDTO);
    });

    verify(categoryRepository, times(1)).findById(categoryId);
  }

  @Test
  @DisplayName("Should be able to create an action with a non existent category")
  public void should_not_be_able_to_create_an_action_with_a_non_existent_category() {
    var categoryId = UUID.randomUUID();
    var organizerId = UUID.randomUUID();

    CreateActionDTO createActionDTO = CreateActionDTO.builder()
        .categoryId(categoryId)
        .organizerId(organizerId)
        .title("title-test")
        .subtitle("test-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .build();

    var category = CategoryEntity.builder()
        .id(categoryId)
        .name("test-name")
        .description("test-description")
        .build();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
    when(organizerRepository.findById(organizerId)).thenReturn(Optional.empty());

    assertThrows(OrganizerNotFoundException.class, () -> {
      this.createActionUseCase.execute(createActionDTO);
    });

    verify(categoryRepository, times(1)).findById(categoryId);
    verify(organizerRepository, times(1)).findById(organizerId);

  }
}
