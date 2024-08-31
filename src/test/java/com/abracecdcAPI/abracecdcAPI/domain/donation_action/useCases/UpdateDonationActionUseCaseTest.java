package com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.dto.UpdateDonationActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.exceptions.DonationActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository.DonationActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateDonationActionUseCaseTest {
    @InjectMocks
    private UpdateDonationActionUseCase updateDonationActionUseCase;

    @Mock
    private DonationActionRepository donationActionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActionRepository actionRepository;

    @Test
    @DisplayName("Should be able to update a donation action")
    public void should_be_able_to_update_a_donation_action(){
        UUID idDonationAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        UUID idAction = UUID.randomUUID();

        User user = User.builder().id(idUser).build();

        ActionEntity action = ActionEntity.builder().id(idAction).build();
        
        UpdateDonationActionDTO updateDonationActionDTO = UpdateDonationActionDTO.builder()
            .id(idDonationAction)
            .value(20)
            .userId(idUser)
            .actionId(idAction)
            .build();
            
        User userToUpdate = User.builder().build();
        ActionEntity actionToUpdate = ActionEntity.builder().build();

        DonationActionEntity donationActionToUpdate = DonationActionEntity.builder()
            .id(idDonationAction)
            .value(10)
            .actionEntity(actionToUpdate)
            .user(userToUpdate)
            .build();
        
        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.of(donationActionToUpdate));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        when(actionRepository.findById(idAction)).thenReturn(Optional.of(action));

        DonationActionEntity donationAction = updateDonationActionUseCase.execute(updateDonationActionDTO);

        verify(donationActionRepository, times(1)).findById(idDonationAction);
        verify(userRepository, times(1)).findById(idUser);
        verify(actionRepository, times(1)).findById(idAction);

        assertEquals(updateDonationActionDTO.getId(), donationAction.getId());
        assertEquals(updateDonationActionDTO.getValue(), donationAction.getValue());
        assertEquals(user, donationAction.getUser());
        assertEquals(action, donationAction.getActionEntity());
    }

    @Test
    @DisplayName("Should not be able to update a donation action when donation action is not found")
    public void should_not_be_able_to_update_a_donation_action_when_donation_action_is_not_found(){
        UUID idDonationAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        UUID idAction = UUID.randomUUID();

        UpdateDonationActionDTO updateDonationActionDTO = UpdateDonationActionDTO.builder()
            .id(idDonationAction)
            .value(20)
            .userId(idUser)
            .actionId(idAction)
            .build();
        
        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.empty());

        assertThrows(DonationActionNotFoundException.class, () -> {
            updateDonationActionUseCase.execute(updateDonationActionDTO);
        });
    }

    @Test
    @DisplayName("Should not be able to update a donation action when user is not found")
    public void should_not_be_able_to_update_a_donation_action_when_user_is_not_found(){
        UUID idDonationAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        UUID idAction = UUID.randomUUID();

        UpdateDonationActionDTO updateDonationActionDTO = UpdateDonationActionDTO.builder()
            .id(idDonationAction)
            .value(20)
            .userId(idUser)
            .actionId(idAction)
            .build();
        
        DonationActionEntity donationActionToUpdate = DonationActionEntity.builder().build();
        
        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.of(donationActionToUpdate));
        when(userRepository.findById(idUser)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            updateDonationActionUseCase.execute(updateDonationActionDTO);
        });
    }

    @Test
    @DisplayName("Should not be able to update a donation action when action is not found")
    public void should_not_be_able_to_update_a_donation_action_when_action_is_not_found(){
        UUID idDonationAction = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        UUID idAction = UUID.randomUUID();

        UpdateDonationActionDTO updateDonationActionDTO = UpdateDonationActionDTO.builder()
            .id(idDonationAction)
            .value(20)
            .userId(idUser)
            .actionId(idAction)
            .build();
        
        DonationActionEntity donationActionToUpdate = DonationActionEntity.builder().build();
        
        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.of(donationActionToUpdate));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(User.builder().build()));
        when(actionRepository.findById(idAction)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            updateDonationActionUseCase.execute(updateDonationActionDTO);
        });
    }
}
