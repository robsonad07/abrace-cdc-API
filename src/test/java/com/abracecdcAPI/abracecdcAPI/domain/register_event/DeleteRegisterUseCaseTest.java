package com.abracecdcAPI.abracecdcAPI.domain.register_event;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.DeleteRegisterUseCase;
import com.abracecdcAPI.abracecdcAPI.exceptions.RegisterNotFoundException;

public class DeleteRegisterUseCaseTest {

    @Mock
    private RegisterRepository registerRepository;

    @InjectMocks
    private DeleteRegisterUseCase deleteRegisterUseCase;

    private UUID registerId;
    private Register register;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        registerId = UUID.randomUUID();
        register = new Register(registerId, "image.jpg", "description");
    }

    @Test
    public void testDeleteRegister_Success() {
        when(registerRepository.findById(registerId)).thenReturn(Optional.of(register));

        String result = deleteRegisterUseCase.execute(registerId);

        assertEquals("Register deleted successfully.", result);
        verify(registerRepository, times(1)).findById(registerId);
        verify(registerRepository, times(1)).deleteById(registerId);
    }

    @Test
    @DisplayName("Should not be able to delete a non-existent register")
    public void testDeleteRegister_RegisterNotFound() {
        
        when(registerRepository.findById(registerId)).thenReturn(Optional.empty());
    
        
        RegisterNotFoundException exception = assertThrows(RegisterNotFoundException.class, () -> deleteRegisterUseCase.execute(registerId));
    
        assertEquals("Register not found.", exception.getMessage());
    
        verify(registerRepository, times(1)).findById(registerId);
        verify(registerRepository, never()).deleteById(registerId);
    }
    
    @Test
    @DisplayName("Should throw an exception when repository throws an exception during delete")
    public void testDeleteRegister_RepositoryException() {
        when(registerRepository.findById(registerId)).thenReturn(Optional.of(register));
        doThrow(new RuntimeException("Database error")).when(registerRepository).deleteById(registerId);
    
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteRegisterUseCase.execute(registerId));
    
        assertEquals("Database error", exception.getMessage());
    
        verify(registerRepository, times(1)).findById(registerId);
        verify(registerRepository, times(1)).deleteById(registerId);
    }
    
}