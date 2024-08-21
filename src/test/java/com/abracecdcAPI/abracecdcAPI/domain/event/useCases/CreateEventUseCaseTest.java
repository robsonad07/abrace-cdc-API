package com.abracecdcAPI.abracecdcAPI.domain.event.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.event.UseCases.CreateEventUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.event.dto.EventDTO;
import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventAlreadyExistsException;
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
public class CreateEventUseCaseTest {
    @InjectMocks
    private CreateEventUseCase createEventUseCase;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private OrganizerRepository organizerRepository;
    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should be able to create an event")
    public void should_be_able_to_create_an_event(){
        var idEvent = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();

        EventDTO eventDTO = new EventDTO("teste", "teste", "teste",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, idAddress);

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

        var eventToCreate = Event.builder()
                .id(idEvent)
                .title("teste")
                .caption("teste")
                .description("teste")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();

        when(eventRepository.save(any(Event.class))).thenReturn(eventToCreate);
        when(eventRepository.findByTitleAndCaption(eventDTO.title(), eventDTO.caption())).thenReturn(Optional.empty());
        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(category));
        when(organizerRepository.findById(idOrganizer)).thenReturn(Optional.of(organizer));
        when(addressRepository.findById(idAddress)).thenReturn(Optional.of(address));

        var eventCreated = createEventUseCase.execute(eventDTO);

        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventArgumentCaptor.capture());
        Event captureEvent = eventArgumentCaptor.getValue();


        assertEquals(eventToCreate, eventCreated);

        assertEquals(eventCreated.getTitle(), captureEvent.getTitle());
        assertEquals(eventCreated.getCaption(), captureEvent.getCaption());
        assertEquals(eventCreated.getDescription(), captureEvent.getDescription());
        assertEquals(eventCreated.getDateTime(), captureEvent.getDateTime());
        assertEquals(eventCreated.getCategory(), captureEvent.getCategory());
        assertEquals(eventCreated.getOrganizer(), captureEvent.getOrganizer());
        assertEquals(eventCreated.getAddress(), captureEvent.getAddress());


        verify(eventRepository, times(1)).findByTitleAndCaption(eventDTO.title(), eventDTO.caption());
        verify(addressRepository, times(1)).findById(idAddress);
        verify(categoryRepository, times(1)).findById(idCategory);
        verify(organizerRepository, times(1)).findById(idOrganizer);
        verify(eventRepository, times(1)).save(any(Event.class));

    }

    @Test
    @DisplayName("Should not be able to create an event with same title and caption")
    public void should_not_be_able_to_create_an_event_with_same_title_and_caption(){
        var idEvent = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();

        EventDTO eventDTO = new EventDTO("teste", "teste", "teste",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, idAddress);

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

        var existingEvent = Event.builder()
                .id(idEvent)
                .title("teste")
                .caption("teste")
                .description("teste")
                .dateTime(LocalDateTime.parse("2002-02-08T16:10:01"))
                .category(category)
                .organizer(organizer)
                .address(address)
                .build();

        when(eventRepository.findByTitleAndCaption(existingEvent.getTitle(), existingEvent.getCaption()))
                .thenReturn(Optional.of(existingEvent));

        assertThrows(EventAlreadyExistsException.class, () -> {
            createEventUseCase.execute(eventDTO);
        });

        verify(eventRepository, times(1)).findByTitleAndCaption(eventDTO.title(), eventDTO.caption());
    }

    @Test
    @DisplayName("Should not be possible to create an event with a non-existent address.")
    public void should_not_be_possible_to_create_an_event_with_a_non_existent_address(){
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();

        EventDTO eventDTO = new EventDTO("teste", "teste", "teste",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, idAddress);

        when(eventRepository.findByTitleAndCaption(eventDTO.title(), eventDTO.caption())).thenReturn(Optional.empty());
        when(addressRepository.findById(idAddress)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> {
           createEventUseCase.execute(eventDTO);
        });

        verify(eventRepository, times(1)).findByTitleAndCaption(eventDTO.title(), eventDTO.caption());
        verify(addressRepository, times(1)).findById(idAddress);
    }

    @Test
    @DisplayName("Should not be possible to create an event with a non-existent category.")
    public void should_not_be_possible_to_create_an_event_with_a_non_existent_category(){
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();

        EventDTO eventDTO = new EventDTO("teste", "teste", "teste",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, idAddress);

        var address = Address.builder()
                .id(idAddress)
                .city("teste")
                .cep("62960-000")
                .road("teste")
                .number(123)
                .complement("teste")
                .build();

        when(eventRepository.findByTitleAndCaption(eventDTO.title(), eventDTO.caption())).thenReturn(Optional.empty());
        when(addressRepository.findById(idAddress)).thenReturn(Optional.of(address));
        when(categoryRepository.findById(idCategory)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            createEventUseCase.execute(eventDTO);
        });

        verify(eventRepository, times(1)).findByTitleAndCaption(eventDTO.title(), eventDTO.caption());
        verify(addressRepository, times(1)).findById(idAddress);
        verify(categoryRepository, times(1)).findById(idCategory);
    }

    @Test
    @DisplayName("Should not be possible to create an event with a non-existent organizer.")
    public void should_not_be_possible_to_create_an_event_with_a_non_existent_organizer(){
        var idCategory = UUID.randomUUID();
        var idOrganizer = UUID.randomUUID();
        var idAddress = UUID.randomUUID();

        EventDTO eventDTO = new EventDTO("teste", "teste", "teste",
                LocalDateTime.parse("2002-02-08T16:10:01"), idCategory, idOrganizer, idAddress);

        var address = Address.builder()
                .id(idAddress)
                .city("teste")
                .cep("62960-000")
                .road("teste")
                .number(123)
                .complement("teste")
                .build();

        var category = CategoryEntity.builder()
                .id(idCategory)
                .name("teste")
                .description("teste")
                .build();

        when(eventRepository.findByTitleAndCaption(eventDTO.title(), eventDTO.caption())).thenReturn(Optional.empty());
        when(addressRepository.findById(idAddress)).thenReturn(Optional.of(address));
        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(category));
        when(organizerRepository.findById(idOrganizer)).thenReturn(Optional.empty());

        assertThrows(OrganizerNotFoundException.class, () -> {
            createEventUseCase.execute(eventDTO);
        });

        verify(eventRepository, times(1)).findByTitleAndCaption(eventDTO.title(), eventDTO.caption());
        verify(addressRepository, times(1)).findById(idAddress);
        verify(categoryRepository, times(1)).findById(idCategory);
        verify(organizerRepository, times(1)).findById(idOrganizer);
    }
}
