package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;


import com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto.DonationEventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UpdateDonationEventUseCaseTest {

    @InjectMocks
    private UpdateDonationUseCase updateDonationUseCase;

    @Mock
    private DonationEventRepository donationEventRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to update an donation event")
    public void Should_be_able_to_update_an_donation_event(){
        UUID idDonationEvent = UUID.randomUUID();
        UUID idEvent = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();

        Event event = Event.builder().build();
        User user = User.builder().build();

        DonationEventDTO donationEventDTO = new DonationEventDTO(15.0, idEvent, idUser);
        DonationEvent donationEventToUpdate = DonationEvent.builder()
                .id(idDonationEvent)
                .value(10.0)
                .event(event)
                .user(user)
                .build();

        DonationEvent donationEventUpdated = DonationEvent.builder()
                .id(idDonationEvent)
                .value(donationEventDTO.value())
                .event(event)
                .user(user)
                .build();

        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(event));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.of(donationEventToUpdate));
        when(donationEventRepository.save(any(DonationEvent.class))).thenReturn(donationEventUpdated);

        DonationEvent donationEventReturn = updateDonationUseCase.execute(idDonationEvent ,donationEventDTO);

        ArgumentCaptor<DonationEvent> donationEventArgumentCaptor = ArgumentCaptor.forClass(DonationEvent.class);
        verify(donationEventRepository).save(donationEventArgumentCaptor.capture());
        DonationEvent captureDonationEvent = donationEventArgumentCaptor.getValue();

        assertEquals(donationEventUpdated, donationEventReturn);

        assertEquals(donationEventReturn.getValue(), captureDonationEvent.getValue());
        assertEquals(donationEventReturn.getEvent(), captureDonationEvent.getEvent());
        assertEquals(donationEventReturn.getUser(), captureDonationEvent.getUser());

        verify(donationEventRepository, times(1)).findById(idDonationEvent);
        verify(eventRepository, times(1)).findById(idEvent);
        verify(userRepository, times(1)).findById(idUser);
        verify(donationEventRepository, times(1)).save(any(DonationEvent.class));
    }


}
