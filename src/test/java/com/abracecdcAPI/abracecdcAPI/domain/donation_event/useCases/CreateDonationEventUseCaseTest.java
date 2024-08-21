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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreateDonationEventUseCaseTest {
    @InjectMocks
    private CreateDonationEventUseCase createDonationEventUseCase;

    @Mock
    private DonationEventRepository donationEventRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to create an donation event")
    public void should_be_able_to_create_an_donation_event(){
        UUID idDonationEvent = UUID.randomUUID();
        UUID idEvent = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();

        Event event = Event.builder().build();
        User user = User.builder().build();

        DonationEventDTO donationEventDTO = new DonationEventDTO(10.0, idEvent, idUser);
        DonationEvent donationEventToCreate = DonationEvent.builder()
                        .id(idDonationEvent)
                        .value(donationEventDTO.value())
                        .event(event)
                        .user(user)
                        .build();


        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(event));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        when(donationEventRepository.save(any(DonationEvent.class))).thenReturn(donationEventToCreate);

        var donationEventCreated = createDonationEventUseCase.execute(donationEventDTO);

        ArgumentCaptor<DonationEvent> donationEventArgumentCaptor = ArgumentCaptor.forClass(DonationEvent.class);
        verify(donationEventRepository).save(donationEventArgumentCaptor.capture());
        DonationEvent captureDonationEvent = donationEventArgumentCaptor.getValue();

        assertEquals(donationEventToCreate, donationEventCreated);

        assertEquals(donationEventCreated.getValue(), captureDonationEvent.getValue());
        assertEquals(donationEventCreated.getEvent(), captureDonationEvent.getEvent());
        assertEquals(donationEventCreated.getUser(), captureDonationEvent.getUser());

        verify(eventRepository, times(1)).findById(idEvent);
        verify(userRepository, times(1)).findById(idUser);
        verify(donationEventRepository, times(1)).save(any(DonationEvent.class));

    }

}
