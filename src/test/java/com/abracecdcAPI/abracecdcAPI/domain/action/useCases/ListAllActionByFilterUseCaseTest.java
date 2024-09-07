package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(MockitoExtension.class)
public class ListAllActionByFilterUseCaseTest {
  @InjectMocks
  private ListAllActionsByFilterUseCase listAllActionsByFilterUseCase;

  @Mock
  private ActionRepository actionRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private OrganizerRepository organizerRepository;

  @Mock
  private AddressRepository addressRepository;

  @Test
  @DisplayName("Should be able to get all actions by filter")
  public void should_be_able_to_get_all_actions_by_filter() {
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

    var firstExistingAction = ActionEntity.builder()
        .id(actionId)
        .title("title")
        .subtitle("first-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .categoryEntity(category)
        .organizerEntity(organizer)
        .addressEntity(address)
        .build();

    var secondExistingAction = ActionEntity.builder()
        .id(actionId)
        .title("title")
        .subtitle("second-subtitle")
        .description("test-description")
        .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
        .categoryEntity(category)
        .organizerEntity(organizer)
        .addressEntity(address)
        .build();

    List<ActionEntity> actionList = new ArrayList<>();
    actionList.add(firstExistingAction);
    actionList.add(secondExistingAction);

    when(listAllActionsByFilterUseCase.execute("title")).thenReturn(actionList);

    var retrivedList = this.listAllActionsByFilterUseCase.execute("title");

    assertEquals(actionList, retrivedList);
    verify(actionRepository, times(1)).findByTitle("title");

  }

  @Test
  @DisplayName("Should be return a empty list when no organizer match filter")
  public void should_be_return_a_empty_list_when_no_action_match_filter() {
    
    List<ActionEntity> actionList = new ArrayList<>();

    when(listAllActionsByFilterUseCase.execute("no-existing-action")).thenReturn(actionList);

    var retrivedList = this.listAllActionsByFilterUseCase.execute("no-existing-action");

    assertEquals(actionList, retrivedList);

  }
}