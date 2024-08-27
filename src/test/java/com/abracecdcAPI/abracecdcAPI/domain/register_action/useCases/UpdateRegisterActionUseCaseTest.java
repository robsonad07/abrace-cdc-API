package com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases;

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

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.dto.UpdateRegisterActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.exceptions.RegisterActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.repository.RegisterActionRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateRegisterActionUseCaseTest {
    
    @InjectMocks
    private UpdateRegisterActionUseCase updateRegisterActionUseCase;

    @Mock
    private RegisterActionRepository registerActionRepository;

    @Mock
    private ActionRepository actionRepository;

    @Test
    @DisplayName("Should be able to update an existing register action")
    public void should_be_able_to_update_an_existing_register_action() {

        UUID id = UUID.randomUUID();
        UUID actionId = UUID.randomUUID();

        var actionEntity = ActionEntity.builder()
            .id(actionId)
            .title("New Action Title")
            .description("New Action Description")
            .build();

        var registerActionEntity = RegisterActionEntity.builder()
            .id(id)
            .urlImage("image/old")
            .description("Old Description")
            .actionEntity(actionEntity)
            .build();

        var updateRegisterActionDTO = UpdateRegisterActionDTO.builder()
            .id(id)
            .urlImage("image/new")
            .description("New Description")
            .actionId(actionId)
            .build();

        when(registerActionRepository.findById(id)).thenReturn(Optional.of(registerActionEntity));
        when(actionRepository.findById(actionId)).thenReturn(Optional.of(actionEntity));
        when(registerActionRepository.save(any(RegisterActionEntity.class))).thenReturn(registerActionEntity);

        var result = updateRegisterActionUseCase.execute(updateRegisterActionDTO);

        assertEquals(actionEntity, result.getActionEntity());
    }


    @Test
    @DisplayName("Should not be able to update a non-existing register action")
    public void should_not_be_able_to_update_a_non_existing_register_action() {
         UUID id = UUID.randomUUID();

        var updateRegisterActionDTO = UpdateRegisterActionDTO.builder()
            .id(id)
            .urlImage("image/new")
            .description("New Description")
            .actionId(UUID.randomUUID())
            .build();

        when(registerActionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegisterActionNotFoundException.class, () -> {
            updateRegisterActionUseCase.execute(updateRegisterActionDTO);
        });
    }


    @Test
    @DisplayName("Should not be able to update a register action with a non-existing action")
    public void should_not_be_able_to_update_a_register_action_with_a_non_existing_action() {
        
        UUID id = UUID.randomUUID();
        UUID nonExistingActionId = UUID.randomUUID();

        var registerActionEntity = RegisterActionEntity.builder()
            .id(id)
            .urlImage("image/old")
            .description("Old Description")
            .actionEntity(null)
            .build();

        var updateRegisterActionDTO = UpdateRegisterActionDTO.builder()
            .id(id)
            .urlImage("image/new")
            .description("New Description")
            .actionId(nonExistingActionId)
            .build();

        when(registerActionRepository.findById(id)).thenReturn(Optional.of(registerActionEntity));
        when(actionRepository.findById(nonExistingActionId)).thenReturn(Optional.empty());

        assertThrows(ActionNotFoundException.class, () -> {
            updateRegisterActionUseCase.execute(updateRegisterActionDTO);
        });
    }
}
