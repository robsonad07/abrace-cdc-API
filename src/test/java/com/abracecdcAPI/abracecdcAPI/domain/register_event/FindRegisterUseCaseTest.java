package com.abracecdcAPI.abracecdcAPI.domain.register_event;

import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.FindRegisterUseCase;

public class FindRegisterUseCaseTest {

    @Mock
    private RegisterRepository registerRepository;

    @InjectMocks
    private FindRegisterUseCase findRegisterUseCase;

    private UUID registerId;
    private Register register;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        registerId = UUID.randomUUID();
        register = new Register(registerId, "https://example.com/image.jpg", "Description");
    }

    @Test
    @DisplayName("Should find and return the register when it exists")
    public void testFindRegister_Success() {
        
        when(registerRepository.findById(registerId)).thenReturn(Optional.of(register));

        Register result = findRegisterUseCase.execute(registerId);

        assertNotNull(result);
        assertEquals(registerId, result.getId());
        assertEquals("https://example.com/image.jpg", result.getUrlImage());
        assertEquals("Description", result.getDescription());

        verify(registerRepository, times(1)).findById(registerId);
    }

    @Test
    @DisplayName("Should throw RegisterNotFoundException when register does not exist")
    public void testFindRegister_RegisterNotFound() {
        
        when(registerRepository.findById(registerId)).thenReturn(Optional.empty());

        RegisterNotFoundException exception = assertThrows(RegisterNotFoundException.class, () -> findRegisterUseCase.execute(registerId));

        assertEquals("Register not found.", exception.getMessage());

        verify(registerRepository, times(1)).findById(registerId);
    }

    @Test
    @DisplayName("Should handle unexpected exceptions")
    public void testFindRegister_UnexpectedException() {
        
        when(registerRepository.findById(registerId)).thenThrow(new RuntimeException("Unexpected error"));

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> findRegisterUseCase.execute(registerId));

        assertEquals("Unexpected error", exception.getMessage());

        verify(registerRepository, times(1)).findById(registerId);
    }
}
