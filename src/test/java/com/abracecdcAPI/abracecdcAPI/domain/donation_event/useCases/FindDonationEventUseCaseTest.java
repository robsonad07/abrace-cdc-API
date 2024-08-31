package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.exceptions.DonationEventNotFoundException;
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
public class FindDonationEventUseCaseTest {

    @InjectMocks
    private FindDonationEventUseCase findDonationEventUseCase;

    @Mock
    private DonationEventRepository donationEventRepository;

    @Test
    @DisplayName("Should be able to find an donation event")
    public void should_be_able_to_find_an_donation_event(){
        UUID idDonationEvent = UUID.randomUUID();
        UUID idEvent = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();

        Event event = Event.builder().build();
        User user = User.builder().build();

        DonationEvent donationEventToFind = DonationEvent.builder()
                .id(idDonationEvent)
                .value(10.0)
                .event(event)
                .user(user)
                .build();

        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.of(donationEventToFind));

        DonationEvent donationEventFinder = findDonationEventUseCase.execute(idDonationEvent);

        ArgumentCaptor<UUID> donationEventArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(donationEventRepository).findById(donationEventArgumentCaptor.capture());
        UUID captureDonationEvent = donationEventArgumentCaptor.getValue();

        assertEquals(donationEventToFind, donationEventFinder);

        assertEquals(donationEventFinder.getId(), captureDonationEvent);

        verify(donationEventRepository, times(1)).findById(idDonationEvent);

    }

    @Test
    @DisplayName("Should not be able to find a non-existent event")
    public void should_be_able_to_find_a_non_existent_event(){
        UUID idDonationEvent = UUID.randomUUID();

        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.empty());

        assertThrows(DonationEventNotFoundException.class, () -> {
            findDonationEventUseCase.execute(idDonationEvent);
        });

        verify(donationEventRepository, times(1)).findById(idDonationEvent);

    }
}
