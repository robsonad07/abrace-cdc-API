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

import com.abracecdcAPI.abracecdcAPI.domain.action.dto.UpdateActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.exceptions.ActionAlredyExistsException;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateActionUseCaseTest {
	@InjectMocks
	private UpdateActionUseCase updateActionUseCase;

	@Mock
	private ActionRepository actionRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private OrganizerRepository organizerRepository;

	@Mock
	private AddressRepository addressRepository;

	@Test
	@DisplayName("Should be able to update an action")
	public void should_be_able_to_update_an_action() {

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

		var existingAction = ActionEntity.builder()
				.id(actionId)
				.title("title-test")
				.subtitle("test-subtitle")
				.description("test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.categoryEntity(category)
				.organizerEntity(organizer)
				.addressEntity(address)
				.build();

		var updateActionDTO = UpdateActionDTO.builder()
				.id(actionId)
				.addressId(addressId)
				.categoryId(categoryId)
				.organizerId(organizerId)
				.title("new-title-test")
				.subtitle("new-test-subtitle")
				.description("new-test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.build();

		var updatedAction = ActionEntity.builder()
				.id(actionId)
				.title("new-title-test")
				.subtitle("new-test-subtitle")
				.description("new-test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.categoryEntity(category)
				.organizerEntity(organizer)
				.addressEntity(address)
				.build();

		when(actionRepository.save(any(ActionEntity.class))).thenReturn(updatedAction);
		when(actionRepository.findById(actionId)).thenReturn(Optional.of(existingAction));
		when(actionRepository.findByTitleAndSubtitle(updateActionDTO.getTitle(), updateActionDTO.getSubtitle()))
				.thenReturn(Optional.empty());
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
		when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(organizer));
		when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

		var retrivedAction = updateActionUseCase.execute(updateActionDTO);

		ArgumentCaptor<ActionEntity> actionArgumentCaptor = ArgumentCaptor.forClass(ActionEntity.class);
		verify(actionRepository).save(actionArgumentCaptor.capture());
		ActionEntity captureAction = actionArgumentCaptor.getValue();

		assertEquals(updatedAction, retrivedAction);

		assertEquals(retrivedAction.getTitle(), captureAction.getTitle());
		assertEquals(retrivedAction.getSubtitle(), captureAction.getSubtitle());
		assertEquals(retrivedAction.getDescription(), captureAction.getDescription());
		assertEquals(retrivedAction.getDateTime(), captureAction.getDateTime());
		assertEquals(retrivedAction.getCategoryEntity(), captureAction.getCategoryEntity());
		assertEquals(retrivedAction.getOrganizerEntity(), captureAction.getOrganizerEntity());
		assertEquals(retrivedAction.getAddressEntity(), captureAction.getAddressEntity());

		verify(actionRepository, times(1)).findByTitleAndSubtitle(updateActionDTO.getTitle(),
				updateActionDTO.getSubtitle());
		verify(addressRepository, times(1)).findById(addressId);
		verify(categoryRepository, times(1)).findById(categoryId);
		verify(organizerRepository, times(1)).findById(organizerId);
		verify(actionRepository, times(1)).save(any(ActionEntity.class));

	}

	@Test
	@DisplayName("should_not_be_able_to_update_a_no_existing_action")
	public void should_not_be_able_to_update_a_no_existing_action() {
		var actionId = UUID.randomUUID();
		var categoryId = UUID.randomUUID();
		var organizerId = UUID.randomUUID();
		var addressId = UUID.randomUUID();

		var updateActionDTO = UpdateActionDTO.builder()
				.id(actionId)
				.addressId(addressId)
				.categoryId(categoryId)
				.organizerId(organizerId)
				.title("title-test")
				.subtitle("test-subtitle")
				.description("test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.build();

		when(actionRepository.findById(actionId)).thenReturn(Optional.empty());

		assertThrows(ActionNotFoundException.class, () -> {
			this.updateActionUseCase.execute(updateActionDTO);
		});

		verify(actionRepository, times(1)).findById(actionId);
	}

	@Test
	@DisplayName("should_not_be_able_to_update_a_no_existing_action")
	public void should_not_be_able_to_update_a_action_with_a_no_existing_address() {
		var actionId = UUID.randomUUID();
		var categoryId = UUID.randomUUID();
		var organizerId = UUID.randomUUID();
		var addressId = UUID.randomUUID();

		var category = CategoryEntity.builder()
				.id(categoryId)
				.name("teste")
				.description("teste")
				.build();

		var address = Address.builder()
				.id(addressId)
				.city("teste")
				.cep("62960-000")
				.road("teste")
				.number(123)
				.complement("teste")
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
				.addressEntity(address)
				.build();

		var updateActionDTO = UpdateActionDTO.builder()
				.id(actionId)
				.addressId(addressId)
				.categoryId(categoryId)
				.organizerId(organizerId)
				.title("title-test")
				.subtitle("test-subtitle")
				.description("test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.build();

		when(actionRepository.findById(actionId)).thenReturn(Optional.of(existingAction));
		when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

		assertThrows(AddressNotFoundException.class, () -> {
			this.updateActionUseCase.execute(updateActionDTO);
		});

		verify(actionRepository, times(1)).findById(actionId);
		verify(addressRepository, times(1)).findById(addressId);
	}

	@Test
	@DisplayName("Should not be able to update a action with a no existing category")
	public void should_not_be_able_to_update_a_action_with_a_no_existing_category() {
		var actionId = UUID.randomUUID();
		var categoryId = UUID.randomUUID();
		var organizerId = UUID.randomUUID();
		var addressId = UUID.randomUUID();

		var category = CategoryEntity.builder()
				.id(categoryId)
				.name("teste")
				.description("teste")
				.build();

		var address = Address.builder()
				.id(addressId)
				.city("teste")
				.cep("62960-000")
				.road("teste")
				.number(123)
				.complement("teste")
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
				.addressEntity(address)
				.build();

		var updateActionDTO = UpdateActionDTO.builder()
				.id(actionId)
				.addressId(addressId)
				.categoryId(categoryId)
				.organizerId(organizerId)
				.title("title-test")
				.subtitle("test-subtitle")
				.description("test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.build();

		when(actionRepository.findById(actionId)).thenReturn(Optional.of(existingAction));
		when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

		assertThrows(CategoryNotFoundException.class, () -> {
			this.updateActionUseCase.execute(updateActionDTO);
		});

		verify(actionRepository, times(1)).findById(actionId);
		verify(addressRepository, times(1)).findById(addressId);
		verify(categoryRepository, times(1)).findById(categoryId);
	}

	@Test
	@DisplayName("Should not be able to update a action with a no existing organizer")
	public void should_not_be_able_to_update_a_action_with_a_no_existing_organizer() {
		var actionId = UUID.randomUUID();
		var categoryId = UUID.randomUUID();
		var organizerId = UUID.randomUUID();
		var addressId = UUID.randomUUID();

		var category = CategoryEntity.builder()
				.id(categoryId)
				.name("teste")
				.description("teste")
				.build();

		var address = Address.builder()
				.id(addressId)
				.city("teste")
				.cep("62960-000")
				.road("teste")
				.number(123)
				.complement("teste")
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
				.addressEntity(address)
				.build();

		var updateActionDTO = UpdateActionDTO.builder()
				.id(actionId)
				.addressId(addressId)
				.categoryId(categoryId)
				.organizerId(organizerId)
				.title("title-test")
				.subtitle("test-subtitle")
				.description("test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.build();

		when(actionRepository.findById(actionId)).thenReturn(Optional.of(existingAction));
		when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
		when(organizerRepository.findById(organizerId)).thenReturn(Optional.empty());

		assertThrows(OrganizerNotFoundException.class, () -> {
			this.updateActionUseCase.execute(updateActionDTO);
		});

		verify(actionRepository, times(1)).findById(actionId);
		verify(addressRepository, times(1)).findById(addressId);
		verify(categoryRepository, times(1)).findById(categoryId);
		verify(organizerRepository, times(1)).findById(organizerId);
	}

	@Test
	@DisplayName("should not be able to update a action with title and subtitle existing")
	public void should_not_be_able_to_update_a_action_with_title_and_subtitle_existing() {
		var actionId = UUID.randomUUID();
		var categoryId = UUID.randomUUID();
		var organizerId = UUID.randomUUID();
		var addressId = UUID.randomUUID();

		var category = CategoryEntity.builder()
				.id(categoryId)
				.name("teste")
				.description("teste")
				.build();

		var address = Address.builder()
				.id(addressId)
				.city("teste")
				.cep("62960-000")
				.road("teste")
				.number(123)
				.complement("teste")
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
				.addressEntity(address)
				.build();

		var actionWithTitleAndSubtitle = ActionEntity.builder()
				.id(UUID.randomUUID())
				.title("new-title-test")
				.subtitle("new-test-subtitle")
				.description("test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.categoryEntity(category)
				.organizerEntity(organizer)
				.addressEntity(address)
				.build();

		var updateActionDTO = UpdateActionDTO.builder()
				.id(actionId)
				.addressId(addressId)
				.categoryId(categoryId)
				.organizerId(organizerId)
				.title("new-title-test")
				.subtitle("new-test-subtitle")
				.description("test-description")
				.dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
				.build();

		when(actionRepository.findById(actionId)).thenReturn(Optional.of(existingAction));
		when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
		when(organizerRepository.findById(organizerId)).thenReturn(Optional.of(organizer));
		when(actionRepository.findByTitleAndSubtitle(updateActionDTO.getTitle(), updateActionDTO.getSubtitle()))
				.thenReturn(Optional.of(actionWithTitleAndSubtitle));

		assertThrows(ActionAlredyExistsException.class, () -> {
			this.updateActionUseCase.execute(updateActionDTO);
		});

		verify(actionRepository, times(1)).findById(actionId);
		verify(addressRepository, times(1)).findById(addressId);
		verify(categoryRepository, times(1)).findById(categoryId);
		verify(organizerRepository, times(1)).findById(organizerId);
		verify(actionRepository, times(1)).findByTitleAndSubtitle(updateActionDTO.getTitle(),
				updateActionDTO.getSubtitle());
	}

}
