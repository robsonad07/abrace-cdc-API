package com.abracecdcAPI.abracecdcAPI.domain.event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.FindEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FindEventUseCaseTest {
    @InjectMocks
    private FindEventUseCase findEventUseCase;

    @Mock
    private EventRepository eventRepository;

    @Test
    @DisplayName("Should be able to find an event")
    public void should_be_able_to_find_an_event(){
        var idEvent = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();

        var category = CategoryEntity.builder()
                .id(idCategory)
                .name("teste")
                .description("teste")
                .build();

        var organizer = OrganizerEntity.builder()
                .id(idOrganizer)
                .cellphone("9999-9999")
                .name("organizer-test")
                .email("organizer@test.com")
                .build();

        var address = Address.builder()
                .id(idAddress)
                .city("teste")
                .cep("62960-000")
                .road("teste")
                .number(123)
                .complement("teste")
                .build();

        var eventToFind = Event.builder()
                .id(idEvent)
                .title("teste")
                .caption("teste")
                .description("teste")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();

        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(eventToFind));

        var findedEvent = findEventUseCase.execute(idEvent);

        assertEquals(eventToFind, findedEvent);

        verify(eventRepository, times(1)).findById(idEvent);
    }

    @Test
    @DisplayName("Should not be able to find a non-existent event")
    public void should_be_able_to_find_a_non_existent_event(){
        var idEvent = UUID.randomUUID();

        when(eventRepository.findById(idEvent)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            findEventUseCase.execute(idEvent);
        });

        verify(eventRepository, times(1)).findById(idEvent);
    }
}
