package com.abracecdcAPI.abracecdcAPI.domain.register_event;

import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.repository.RegisterRepository;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.useCases.GetAllRegisterUserCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetAllRegisterUseCaseTest {

    @Mock
    private RegisterRepository registerRepository;

    @InjectMocks
    private GetAllRegisterUserCase getAllRegisterUserCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all registers")
    public void testGetAllRegisters() {
        Register register1 = new Register(UUID.randomUUID(), "url1", "description1");
        Register register2 = new Register(UUID.randomUUID(), "url2", "description2");
        List<Register> mockRegisters = Arrays.asList(register1, register2);

        when(registerRepository.findAll()).thenReturn(mockRegisters);

        List<Register> result = getAllRegisterUserCase.execute();

        assertEquals(2, result.size());
        assertEquals("url1", result.get(0).getUrlImage());
        assertEquals("url2", result.get(1).getUrlImage());

        verify(registerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return an empty list if no registers are found")
    public void testGetAllRegisters_EmptyList() {
        when(registerRepository.findAll()).thenReturn(List.of());

        List<Register> result = getAllRegisterUserCase.execute();

        assertEquals(0, result.size());

        verify(registerRepository, times(1)).findAll();
    }
}
