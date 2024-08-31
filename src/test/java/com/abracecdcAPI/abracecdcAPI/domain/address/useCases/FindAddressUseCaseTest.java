package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FindAddressUseCaseTest {
    
    @InjectMocks
    private FindAddressUseCase findAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should be able to find an address")
    void should_be_able_to_find_an_address() {
        var idAddress = UUID.randomUUID();
        
        var address = Address.builder()
            .id(idAddress)
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        when(addressRepository.findById(idAddress)).thenReturn(Optional.of(address));

        var retrivedAddress = findAddressUseCase.execute(idAddress);

        assertEquals(retrivedAddress, address);

        verify(addressRepository, times(1)).findById(idAddress);
    }

    @Test
    @DisplayName("Should not be able to find an address")
    void should_not_be_able_to_find_an_address() {
        var idAddress = UUID.randomUUID();

        when(addressRepository.findById(idAddress)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> {
            findAddressUseCase.execute(idAddress);
        });

        verify(addressRepository, times(1)).findById(idAddress);
    }
}
