package com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.exceptions.RegisterActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.repository.RegisterActionRepository;

@ExtendWith(MockitoExtension.class)
public class GetRegisterActionByIdUseCaseTest {
    
    @InjectMocks
    private GetRegisterActionByIdUseCase getRegisterActionByIdUseCase;

    @Mock
    private RegisterActionRepository registerActionRepository;

    @Test
    @DisplayName("Should be able to get a register action by id")
    public void should_be_able_to_get_a_register_action_by_id() {
        var registerActionToGetById = RegisterActionEntity.builder()
                .id(UUID.randomUUID())
                .urlImage("image/test")
                .description("test")
                .build();

        when(registerActionRepository.findById(registerActionToGetById.getId())).thenReturn(Optional.of(registerActionToGetById));

        RegisterActionEntity result = getRegisterActionByIdUseCase.execute(registerActionToGetById.getId());

        assertEquals(registerActionToGetById, result);
        verify(registerActionRepository).findById(registerActionToGetById.getId());
    }


    @Test
    @DisplayName("Should not be able to get a register action when it is not found")
    public void should_not_be_able_to_get_a_register_action_when_it_is_empty() {
        UUID nonExistentRegisterActionId = UUID.randomUUID();

        when(registerActionRepository.findById(nonExistentRegisterActionId)).thenReturn(Optional.empty());

        assertThrows(RegisterActionNotFoundException.class, () -> {
            getRegisterActionByIdUseCase.execute(nonExistentRegisterActionId);
        });
    }
}
