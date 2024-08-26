package com.abracecdcAPI.abracecdcAPI.domain.donation_event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository.DonationEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetAllDonationEventUseCaseTest {

    @InjectMocks
    private GetAllDonationEventUseCase getAllDonationEventUseCase;

    @Mock
    private DonationEventRepository donationEventRepository;

    @Test
    @DisplayName("Should be able to get all donation events")
    public void should_be_able_to_get_all_donation_events(){
        List<DonationEvent> donationsEventsToFind = new ArrayList<>();
        donationsEventsToFind.add(DonationEvent.builder().build());
        donationsEventsToFind.add(DonationEvent.builder().build());

        when(donationEventRepository.findAll()).thenReturn(donationsEventsToFind);

        List<DonationEvent> findersDonationEvents = getAllDonationEventUseCase.execute();

        assertEquals(donationsEventsToFind, findersDonationEvents);
        verify(donationEventRepository, times(1)).findAll();

    }
}
