package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;


import com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto.DonationEventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.DonationEventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.UserNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.ValueOfDonationEventIsNegativeException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UpdateDonationEventUseCaseTest {

    @InjectMocks
    private UpdateDonationEventUseCase updateDonationEventUseCase;

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

        DonationEvent donationEventReturn = updateDonationEventUseCase.execute(idDonationEvent ,donationEventDTO);

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

    @Test
    @DisplayName("Should not be possible to update an donation event with a negative value.")
    public void should_not_be_possible_to_create_an_event_with_a_negative_value(){
        UUID idDonationEvent = UUID.randomUUID();
        UUID idEvent = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();

        DonationEventDTO donationEventDTO = new DonationEventDTO(-10.0, idEvent, idUser);

        assertThrows(ValueOfDonationEventIsNegativeException.class, () -> {
            updateDonationEventUseCase.execute(idDonationEvent ,donationEventDTO);
        });

    }

    @Test
    @DisplayName("Should not be possible to update an non-existent donation event")
    public void should_not_be_possible_to_update_an_non_existent_event_donation(){
        UUID idDonationEvent = UUID.randomUUID();
        UUID idEvent = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();

        DonationEventDTO donationEventDTO = new DonationEventDTO(10.0, idEvent, idUser);

        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.empty());

        assertThrows(DonationEventNotFoundException.class, () -> {
            updateDonationEventUseCase.execute(idDonationEvent ,donationEventDTO);
        });

        verify(donationEventRepository, times(1)).findById(idDonationEvent);
    }

    @Test
    @DisplayName("Should not be possible to create an donation event with a non-existent event.")
    public void should_not_be_possible_to_create_an_event_donation_with_a_non_existent_event(){
        UUID idDonationEvent = UUID.randomUUID();
        UUID idEvent = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();

        DonationEventDTO donationEventDTO = new DonationEventDTO(10.0, idEvent, idUser);

        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.of(DonationEvent.builder().id(idDonationEvent).build()));
        when(eventRepository.findById(idEvent)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            updateDonationEventUseCase.execute(idDonationEvent ,donationEventDTO);
        });

        verify(donationEventRepository, times(1)).findById(idDonationEvent);
        verify(eventRepository, times(1)).findById(idEvent);
    }

    @Test
    @DisplayName("Should not be possible to create an donation event with a non-existent user.")
    public void should_not_be_possible_to_create_an_event_donation_with_a_non_existent_user(){
        UUID idDonationEvent = UUID.randomUUID();
        UUID idEvent = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();

        DonationEventDTO donationEventDTO = new DonationEventDTO(10.0, idEvent, idUser);

        when(donationEventRepository.findById(idDonationEvent)).thenReturn(Optional.of(DonationEvent.builder().id(idDonationEvent).build()));
        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(Event.builder().id(idEvent).build()));
        when(userRepository.findById(idUser)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            updateDonationEventUseCase.execute(idDonationEvent ,donationEventDTO);
        });

        verify(donationEventRepository, times(1)).findById(idDonationEvent);
        verify(eventRepository, times(1)).findById(idEvent);
        verify(userRepository, times(1)).findById(idUser);
    }

}
