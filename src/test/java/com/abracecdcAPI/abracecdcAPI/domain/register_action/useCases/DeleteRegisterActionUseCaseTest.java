package com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases;

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
public class DeleteRegisterActionUseCaseTest {

    @InjectMocks
    private DeleteRegisterActionUseCase deleteRegisterActionUseCase;

    @Mock
    private RegisterActionRepository registerActionRepository;

    @Test
    @DisplayName("Should be able to delete a register action")
    public void should_be_able_to_delete_a_register_action() {

        UUID id = UUID.randomUUID();

        var registerActionEntity = RegisterActionEntity.builder()
                .id(id)
                .urlImage("image/test")
                .description("test")
                .build();

        when(registerActionRepository.findById(id)).thenReturn(Optional.of(registerActionEntity));

        deleteRegisterActionUseCase.execute(id);

        verify(registerActionRepository).delete(registerActionEntity);
    }

    @Test
    @DisplayName("Should not be able to delete a register action when it is not found")
    public void should_not_be_able_to_delete_a_register_action_when_it_is_empty() {

        UUID id = UUID.randomUUID();

        when(registerActionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegisterActionNotFoundException.class, () -> {
            deleteRegisterActionUseCase.execute(id);
        });
    }
}
