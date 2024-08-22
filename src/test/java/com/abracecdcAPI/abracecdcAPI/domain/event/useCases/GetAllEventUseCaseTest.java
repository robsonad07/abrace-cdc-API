package com.abracecdcAPI.abracecdcAPI.domain.event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.GetAllEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetAllEventUseCaseTest {
    @InjectMocks
    private GetAllEventUseCase getAllEventUseCase;

    @Mock
    private EventRepository eventRepository;

    @Test
    @DisplayName("Should be able to get all events")
    public void should_be_able_to_get_all_events(){
        List<Event> eventsToFind = new ArrayList<>();
        eventsToFind.add(Event.builder().build());
        eventsToFind.add(Event.builder().build());

        when(eventRepository.findAll()).thenReturn(eventsToFind);

        List<Event> findersEvents = getAllEventUseCase.execute();

        assertEquals(eventsToFind, findersEvents);
        verify(eventRepository, times(1)).findAll();
    }
}
