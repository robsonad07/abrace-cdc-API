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
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.exceptions.DonationActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository.DonationActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class GetDonationActionByIdUseCaseTest {
    @InjectMocks
    private GetDonationActionByIdUseCase getDonationActionByIdUseCase;

    @Mock
    private DonationActionRepository donationActionRepository;

    @Test
    @DisplayName("Should be able to get an donation action by id")
    public void should_be_able_to_get_an_donation_action_by_id(){
        UUID idDonationAction = UUID.randomUUID();

        User user = User.builder().build();
        ActionEntity action = ActionEntity.builder().build();
        
        DonationActionEntity donationActionToGet = DonationActionEntity.builder()
        .id(idDonationAction)
        .value(10)
        .actionEntity(action)
        .user(user)
        .build();
        
        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.of(donationActionToGet));

        DonationActionEntity donationAction = getDonationActionByIdUseCase.execute(idDonationAction);

        verify(donationActionRepository, times(1)).findById(idDonationAction);
        assertEquals(donationActionToGet, donationAction);
    }

    @Test
    @DisplayName("Should not be able to get a non-existent donation action by id")
    public void should_not_be_able_to_get_a_non_existent_donation_action_by_id(){
        UUID idDonationAction = UUID.randomUUID();

        when(donationActionRepository.findById(idDonationAction)).thenReturn(Optional.empty());

        assertThrows(DonationActionNotFoundException.class, () -> {
            getDonationActionByIdUseCase.execute(idDonationAction);
        });

        verify(donationActionRepository, times(1)).findById(idDonationAction);
    }
}
