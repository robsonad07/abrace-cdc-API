package com.abracecdcAPI.abracecdcAPI.domain.register_event;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.abracecdcAPI.abracecdcAPI.domain.event.repository.EventRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.EventNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.UpdateRegisterUseCase;

public class UpdateRegisterUseCaseTest {

    @InjectMocks
    private UpdateRegisterUseCase updateRegisterUseCase;

    @Mock
    private RegisterRepository registerRepository;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Must update an existing record successfully")
    void testUpdateRegister_Success() {
        UUID registerId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        Register existingRegister = new Register(registerId, "url_old", "description_old", null);
        Event event = new Event();
        RegisterDTO registerDTO = new RegisterDTO("url_new", "description_new", eventId);

        when(registerRepository.findById(registerId)).thenReturn(Optional.of(existingRegister));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(registerRepository.save(any(Register.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Register result = updateRegisterUseCase.execute(registerId, registerDTO);

        assertEquals(registerId, result.getId());
        assertEquals("url_new", result.getUrlImage());
        assertEquals("description_new", result.getDescription());
        assertEquals(event, result.getEvent());

        verify(registerRepository, times(1)).findById(registerId);
        verify(eventRepository, times(1)).findById(eventId);
        verify(registerRepository, times(1)).save(result);
    }

    @Test
    @DisplayName("Should throw RegisterNotFoundException if record does not exist")
    void testUpdateRegister_RegisterNotFound() {
        UUID registerId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        RegisterDTO registerDTO = new RegisterDTO("url_new", "description_new", eventId);
    
        when(registerRepository.findById(registerId)).thenReturn(Optional.empty());
    
        RegisterNotFoundException exception = assertThrows(RegisterNotFoundException.class,
                () -> updateRegisterUseCase.execute(registerId, registerDTO));
    
        assertEquals("Register not found.", exception.getMessage());
    
        verify(registerRepository, times(1)).findById(registerId);
        verify(eventRepository, never()).findById(any(UUID.class));
        verify(registerRepository, never()).save(any(Register.class));
    }
    
    @Test
    @DisplayName("Should throw EventNotFoundException if event does not exist")
    void testUpdateRegister_EventNotFound() {
        UUID registerId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        RegisterDTO registerDTO = new RegisterDTO("url_new", "description_new", eventId);
        Register existingRegister = new Register(registerId, "url_old", "description_old", null);

        when(registerRepository.findById(registerId)).thenReturn(Optional.of(existingRegister));
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        EventNotFoundException exception = assertThrows(EventNotFoundException.class,
                () -> updateRegisterUseCase.execute(registerId, registerDTO));

        assertEquals("Event not found.", exception.getMessage());

        verify(registerRepository, times(1)).findById(registerId);
        verify(eventRepository, times(1)).findById(eventId);
        verify(registerRepository, never()).save(any(Register.class));
    }
}
