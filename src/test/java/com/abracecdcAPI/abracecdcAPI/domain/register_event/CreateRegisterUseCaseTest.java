package com.abracecdcAPI.abracecdcAPI.domain.register_event;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.CreateRegisterUseCase;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class CreateRegisterUseCaseTest {

    @InjectMocks
    private CreateRegisterUseCase createRegisterUseCase;

    @Mock
    private RegisterRepository registerRepository;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void should_be_able_to_create_a_register() {
        var registerDTO = new RegisterDTO("http://example.com/image.jpg", "A register description", UUID.randomUUID());
        var event = new Event();
        var newRegister = new Register(registerDTO.urlImage(), registerDTO.description(), event);

        when(registerRepository.findByUrlImage(registerDTO.urlImage())).thenReturn(Optional.empty());
        when(eventRepository.findById(registerDTO.event_id())).thenReturn(Optional.of(event));
        when(registerRepository.save(newRegister)).thenReturn(newRegister);

        var createdRegister = createRegisterUseCase.execute(registerDTO);

        assertEquals(newRegister, createdRegister);
    }

    @Test
    public void should_not_create_register_if_already_exists() {
        var registerDTO = new RegisterDTO("http://example.com/image.jpg", "A register description", UUID.randomUUID());

        when(registerRepository.findByUrlImage(registerDTO.urlImage())).thenReturn(Optional.of(new Register()));

        RegisterAlreadyExistsException exception = assertThrows(RegisterAlreadyExistsException.class,
                () -> createRegisterUseCase.execute(registerDTO));

        assertEquals("Register with URL image " + registerDTO.urlImage() + " already exists.", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_event_not_found() {
        var registerDTO = new RegisterDTO("http://example.com/image.jpg", "A register description", UUID.randomUUID());
        
        when(registerRepository.findByUrlImage(registerDTO.urlImage())).thenReturn(Optional.empty());
        when(eventRepository.findById(registerDTO.event_id())).thenReturn(Optional.empty());
        
        EventNotFoundException exception = assertThrows(EventNotFoundException.class,
                () -> createRegisterUseCase.execute(registerDTO));
        
        assertEquals("Event with ID " + registerDTO.event_id() + " not found.", exception.getMessage());
    }
    
    
}
