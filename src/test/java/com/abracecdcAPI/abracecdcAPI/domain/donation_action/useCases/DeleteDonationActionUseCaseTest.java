package com.abracecdcAPI.abracecdcAPI.domain.donation_action.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.exceptions.DonationActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository.DonationActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class DeleteDonationActionUseCaseTest {
    @InjectMocks
    private DeleteDonationActionUseCase deleteDonationActionUseCase;

    @Mock
    private DonationActionRepository donationActionRepository;

    @Test
    @DisplayName("Should be able to delete an donation action")
    public void should_be_able_to_delete_an_donation_action(){
        UUID idDonationAction = UUID.randomUUID();

        User user = User.builder().build();
        ActionEntity action = ActionEntity.builder().build();
        
        DonationActionEntity donationActionToDelete = DonationActionEntity.builder()
        .id(idDonationAction)
        .value(10)
        .actionEntity(action)
        .user(user)
        .build();
        
        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.of(donationActionToDelete));
        doNothing().when(donationActionRepository).delete(donationActionToDelete);

        deleteDonationActionUseCase.execute(idDonationAction);
        
        verify(donationActionRepository, times(1)).findById(idDonationAction);
        verify(donationActionRepository, times(1)).delete(donationActionToDelete);

        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.empty());
        
        Optional<DonationActionEntity> donationActionOptional = this.donationActionRepository.findById(idDonationAction);

        assertFalse(donationActionOptional.isPresent());
    }

    @Test
    @DisplayName("Should not be able to delete a non-existent donation action")
    public void should_not_be_able_to_delete_a_non_existent_donation_action(){
        UUID idDonationAction = UUID.randomUUID();

        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.empty());

        Optional<DonationActionEntity> donationActionOptional = this.donationActionRepository.findById(idDonationAction);

        verify(donationActionRepository, times(1)).findById(idDonationAction);
        
        assertFalse(donationActionOptional.isPresent());

        assertThrows(DonationActionNotFoundException.class, () -> {
            deleteDonationActionUseCase.execute(idDonationAction);
        });
    }
}
