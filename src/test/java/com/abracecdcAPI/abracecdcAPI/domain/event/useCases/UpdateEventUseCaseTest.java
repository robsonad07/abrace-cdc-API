package com.abracecdcAPI.abracecdcAPI.domain.event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.UpdateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.dto.EventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UpdateEventUseCaseTest {
    @InjectMocks
    private UpdateEventUseCase updateEventUseCase;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private OrganizerRepository organizerRepository;
    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should be able to update an event")
    public void should_be_able_to_update_an_event(){
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

        var eventToUpdate = Event.builder()
                .id(idEvent)
                .title("teste")
                .caption("teste")
                .description("teste")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();

        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(eventToUpdate));
        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(category));
        when(organizerRepository.findById(idOrganizer)).thenReturn(Optional.of(organizer));
        when(addressRepository.findById(idAddress)).thenReturn(Optional.of(address));

        var updatedEvent = Event.builder()
                .id(idEvent)
                .title("teste atualizado")
                .caption("teste atualizado")
                .description("teste atualizado")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();

        when(eventRepository.save(updatedEvent)).thenReturn(updatedEvent);

        EventDTO eventDTO = new EventDTO("teste atualizado", "teste atualizado", "teste atualizado",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, idAddress);

        var event = updateEventUseCase.execute(idEvent, eventDTO);

        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventArgumentCaptor.capture());
        Event captureEvent = eventArgumentCaptor.getValue();

        assertEquals(updatedEvent, event);

        assertEquals(event.getTitle(), captureEvent.getTitle());
        assertEquals(event.getCaption(), captureEvent.getCaption());
        assertEquals(event.getDescription(), captureEvent.getDescription());
        assertEquals(event.getDateTime(), captureEvent.getDateTime());
        assertEquals(event.getCategory(), captureEvent.getCategory());
        assertEquals(event.getOrganizer(), captureEvent.getOrganizer());
        assertEquals(event.getAddress(), captureEvent.getAddress());


        verify(eventRepository, times(1)).findById(idEvent);
        verify(addressRepository, times(1)).findById(idAddress);
        verify(categoryRepository, times(1)).findById(idCategory);
        verify(organizerRepository, times(1)).findById(idOrganizer);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should not be able to update a non-existing event")
    public void should_not_be_able_to_update_a_non_existing_event(){
        var idEvent = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();


        EventDTO eventDTO = new EventDTO("teste atualizado", "teste atualizado", "teste atualizado",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, idAddress);


        when(eventRepository.findById(idEvent)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundException.class, () -> {
            updateEventUseCase.execute(idEvent, eventDTO);
        });

        verify(eventRepository, times(1)).findById(idEvent);
    }

    @Test
    @DisplayName("Should not be able to update a event with a non-existing address")
    public void should_not_be_able_to_update_a_event_with_a_non_existing_address(){
        var idEvent = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();
        var newIdAddress = UUID.randomUUID();


        EventDTO eventDTO = new EventDTO("teste atualizado", "teste atualizado", "teste atualizado",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, newIdAddress);

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

        var eventToUpdate = Event.builder()
                .id(idEvent)
                .title("teste")
                .caption("teste")
                .description("teste")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();


        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(eventToUpdate));
        when(addressRepository.findById(newIdAddress)).thenReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> {
            updateEventUseCase.execute(idEvent, eventDTO);
        });

        verify(eventRepository, times(1)).findById(idEvent);
        verify(addressRepository, times(1)).findById(newIdAddress);
    }

    @Test
    @DisplayName("Should not be able to update a event with a non-existing category")
    public void should_not_be_able_to_update_a_event_with_a_non_existing_category(){
        var idEvent = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();
        var newIdCategory = UUID.randomUUID();


        EventDTO eventDTO = new EventDTO("teste atualizado", "teste atualizado", "teste atualizado",
                LocalDateTime.parse("2002-02-08T16:10:01"), newIdCategory, idOrganizer, idAddress);

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

        var eventToUpdate = Event.builder()
                .id(idEvent)
                .title("teste")
                .caption("teste")
                .description("teste")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();


        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(eventToUpdate));
        when(addressRepository.findById(idAddress)).thenReturn(Optional.of(address));
        when(categoryRepository.findById(newIdCategory)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> {
            updateEventUseCase.execute(idEvent, eventDTO);
        });

        verify(eventRepository, times(1)).findById(idEvent);
        verify(addressRepository, times(1)).findById(idAddress);
        verify(categoryRepository, times(1)).findById(newIdCategory);
    }

    @Test
    @DisplayName("Should not be able to update a event with a non-existing organizer")
    public void should_not_be_able_to_update_a_event_with_a_non_existing_organizer(){
        var idEvent = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();
        var newIdOrganizer = UUID.randomUUID();


        EventDTO eventDTO = new EventDTO("teste atualizado", "teste atualizado", "teste atualizado",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, newIdOrganizer, idAddress);

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

        var eventToUpdate = Event.builder()
                .id(idEvent)
                .title("teste")
                .caption("teste")
                .description("teste")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();


        when(eventRepository.findById(idEvent)).thenReturn(Optional.of(eventToUpdate));
        when(addressRepository.findById(idAddress)).thenReturn(Optional.of(address));
        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(category));
        when(organizerRepository.findById(newIdOrganizer)).thenReturn(Optional.empty());

        assertThrows(OrganizerNotFoundException.class, () -> {
            updateEventUseCase.execute(idEvent, eventDTO);
        });

        verify(eventRepository, times(1)).findById(idEvent);
        verify(addressRepository, times(1)).findById(idAddress);
        verify(categoryRepository, times(1)).findById(idCategory);
        verify(organizerRepository, times(1)).findById(newIdOrganizer);
    }
}
