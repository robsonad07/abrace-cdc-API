package com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.dto.CreateDonationActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository.DonationActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ValueOfDonationActionIsNegativeException;

@ExtendWith(MockitoExtension.class)
public class CreateDonationActionUseCaseTest {
    @InjectMocks
    private CreateDonationActionUseCase createDonationActionUseCase;

    @Mock
    private DonationActionRepository donationActionRepository;

    @Mock
    private ActionRepository actionRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to create a donation action")
    public void should_be_able_to_create_donation_action() {
        UUID idDonationAction = UUID.randomUUID();
        UUID idAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
       
        ActionEntity action = ActionEntity.builder().build();
        User user = User.builder().build();
        int value = 10;

        CreateDonationActionDTO createDonationActionDTO = new CreateDonationActionDTO();
        createDonationActionDTO.setValue(value);
        createDonationActionDTO.setActionId(idAction);
        createDonationActionDTO.setUserId(idUser);

        DonationActionEntity donationActionToCreate = DonationActionEntity.builder()
            .id(idDonationAction)
            .value(value)
            .actionEntity(action)
            .user(user)
            .build();

        when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        when(actionRepository.findById(idAction)).thenReturn(Optional.of(action));
        when(donationActionRepository.save(any(DonationActionEntity.class))).thenReturn(donationActionToCreate);

        DonationActionEntity donationActionCreated = createDonationActionUseCase.execute(createDonationActionDTO);

        ArgumentCaptor<DonationActionEntity> donationActionEntityArgumentCaptor = ArgumentCaptor.forClass(DonationActionEntity.class);
        verify(donationActionRepository).save(donationActionEntityArgumentCaptor.capture());
        DonationActionEntity captureDonationAction = donationActionEntityArgumentCaptor.getValue();

        assertEquals(donationActionCreated, donationActionToCreate);

        assertEquals(donationActionCreated.getValue(), captureDonationAction.getValue());
        assertEquals(donationActionCreated.getActionEntity(), captureDonationAction.getActionEntity());
        assertEquals(donationActionCreated.getUser(), captureDonationAction.getUser());

        verify(userRepository, times(1)).findById(idUser);
        verify(actionRepository, times(1)).findById(idAction);
        verify(donationActionRepository, times(1)).save(any(DonationActionEntity.class));
    }

    @Test
    @DisplayName("Should not be possible to create a donation action with a negative value")
    public void should_not_be_possible_to_create_donation_action_with_a_negative_value() {
        UUID idAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        int value = -10;

        CreateDonationActionDTO createDonationActionDTO = new CreateDonationActionDTO();
        createDonationActionDTO.setValue(value);
        createDonationActionDTO.setActionId(idAction);
        createDonationActionDTO.setUserId(idUser);

        when(userRepository.findById(idUser)).thenReturn(Optional.empty());
        when(actionRepository.findById(idAction)).thenReturn(Optional.empty());
    
        assertThrows(ValueOfDonationActionIsNegativeException.class, () -> {
            createDonationActionUseCase.execute(createDonationActionDTO);
        });

        verify(userRepository, times(1)).findById(idUser);
        verify(actionRepository, times(1)).findById(idAction);
        verify(donationActionRepository, never()).save(any(DonationActionEntity.class));
    }

    @Test
    @DisplayName("Should not be possible to create a donation action with a non-existent user")
    public void should_not_be_possible_to_create_donation_action_with_a_non_existent_user() {
        UUID idAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        int value = 10;

        CreateDonationActionDTO createDonationActionDTO = new CreateDonationActionDTO();
        createDonationActionDTO.setValue(value);
        createDonationActionDTO.setActionId(idAction);
        createDonationActionDTO.setUserId(idUser);

        when(userRepository.findById(idUser)).thenReturn(Optional.empty());
        when(actionRepository.findById(idAction)).
        thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            createDonationActionUseCase.execute(createDonationActionDTO);
        });

        verify(userRepository, times(1)).findById(idUser);
        verify(actionRepository, times(1)).findById(idAction);
        verify(donationActionRepository, never()).save(any(DonationActionEntity.class));
    }

    @Test
    @DisplayName("Should not be possible to create a donation action with a non-existent action")
    public void should_not_be_possible_to_create_donation_action_with_a_non_existent_action() {
        UUID idAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        int value = 10;

        CreateDonationActionDTO createDonationActionDTO = new CreateDonationActionDTO();
        createDonationActionDTO.setValue(value);
        createDonationActionDTO.setActionId(idAction);
        createDonationActionDTO.setUserId(idUser);

        when(userRepository.findById(idUser)).thenReturn(Optional.of(User.builder().build()));
        when(actionRepository.findById(idAction)).
        thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            createDonationActionUseCase.execute(createDonationActionDTO);
        });

        verify(userRepository, times(1)).findById(idUser);
        verify(actionRepository, times(1)).findById(idAction);
        verify(donationActionRepository, never()).save(any(DonationActionEntity.class));
    }
}
