package com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases;

import java.util.UUID;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.dto.CreateRegisterActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.repository.RegisterActionRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CreateRegisterActionUseCaseTest {

    @InjectMocks
    private CreateRegisterActionUseCase createRegisterActionUseCase;

    @Mock
    private RegisterActionRepository registerActionRepository;

    @Mock
    private ActionRepository actionRepository;

    @Test
    @DisplayName("Should be able to create a register action")
    public void should_be_able_to_create_an_register_action() {

        UUID id = UUID.randomUUID();
        UUID idCategory = UUID.randomUUID();
        UUID idOrganizer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        var categoryEntity = CategoryEntity.builder()
            .id(idCategory)
            .name("test")
            .description("test")
            .build();

        var organizerEntity = OrganizerEntity.builder()
            .id(idOrganizer)
            .cellphone("9999-9999")
            .name("organizer-test")
            .email("organizer@test.com")
            .build();

        var address = Address.builder()
            .id(idAddress)
            .city("test")
            .cep("62960-000")
            .road("test")
            .number(123)
            .complement("test")
            .build();

        var actionEntity = ActionEntity.builder()
            .id(id)
            .title("test")
            .subtitle("test")
            .description("test")
            .duration("4 hours")
            .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
            .categoryEntity(categoryEntity)
            .organizerEntity(organizerEntity)
            .addressEntity(address)
            .build();

        var createRegisterActionDTO = CreateRegisterActionDTO.builder()
            .actionId(id)
            .urlImage("image/test")
            .description("test")
            .build();

        var registerActionEntity = RegisterActionEntity.builder()
            .urlImage(createRegisterActionDTO.getUrlImage())
            .description(createRegisterActionDTO.getDescription())
            .actionEntity(actionEntity)
            .build();

        when(actionRepository.findById(id)).thenReturn(Optional.of(actionEntity));
        when(registerActionRepository.save(any(RegisterActionEntity.class))).thenReturn(registerActionEntity);

        
        var registerCreated = createRegisterActionUseCase.execute(createRegisterActionDTO);

        assertEquals(registerActionEntity, registerCreated);
    }

    @Test
    @DisplayName("Should not be able to create register action when action is not found")
    public void should_not_be_able_to_create_register_action_when_action_not_found() {
        
        var actionId = UUID.randomUUID();
        var createRegisterActionDTO = CreateRegisterActionDTO.builder()
                .actionId(actionId)
                .urlImage("image/test")
                .description("test")
                .build();

        
        when(actionRepository.findById(actionId)).thenReturn(Optional.empty());

        
        assertThrows(ActionNotFoundException.class, 
                     () -> createRegisterActionUseCase.execute(createRegisterActionDTO));
    }
}
