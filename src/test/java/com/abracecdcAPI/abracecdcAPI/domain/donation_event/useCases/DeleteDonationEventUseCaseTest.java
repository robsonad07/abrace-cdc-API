package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;


import com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto.DonationEventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.exceptions.DonationEventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeleteDonationEventUseCaseTest {

    @InjectMocks
    private DeleteDonationEventUseCase deleteDonationEventUseCase;

    @Mock
    private DonationEventRepository donationEventRepository;

    @Test
    @DisplayName("Should be able to delete an donation event")
    public void should_be_able_to_delete_an_donation_event(){
        UUID idDonationEvent = UUID.randomUUID();

        Event event = Event.builder().build();
        User user = User.builder().build();


        DonationEvent donationEventToDelete = DonationEvent.builder()
                .id(idDonationEvent)
                .value(10.0)
                .event(event)
                .user(user)
                .build();

        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.of(donationEventToDelete));
        doNothing().when(donationEventRepository).deleteById(idDonationEvent);

        deleteDonationEventUseCase.execute(idDonationEvent);

        ArgumentCaptor<UUID> donationEventArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(donationEventRepository).findById(donationEventArgumentCaptor.capture());
        UUID captureDonationEvent = donationEventArgumentCaptor.getValue();

        assertEquals(idDonationEvent, captureDonationEvent);

        verify(donationEventRepository, times(1)).findById(idDonationEvent);
        verify(donationEventRepository, times(1)).deleteById(idDonationEvent);
    }

    @Test
    @DisplayName("Should be able to delete an non-existent donation event")
    public void should_be_able_to_delete_an_non_existent_donation_event(){
        UUID idDonationEvent = UUID.randomUUID();

        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.empty());

        assertThrows(DonationEventNotFoundException.class, () -> {
            deleteDonationEventUseCase.execute(idDonationEvent);
        });

    }
}
