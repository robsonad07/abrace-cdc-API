package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.address.dto.AddressDTO;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateAddressUseCaseTest {
    @InjectMocks
    private UpdateAddressUseCase updateAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should be able to update an address")
    public void should_be_able_to_update_an_address() {
        var id = UUID.randomUUID();

        var address = Address.builder()
            .id(id)
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();
        
        var addressDTO = AddressDTO.builder()
            .id(id)
            .city("cidade test")
            .cep("12345678 test")
            .road("Rua teste test")
            .number(123)
            .complement("complemento test")
            .build();

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        Address newAddress = updateAddressUseCase.execute(address.getId(), addressDTO);

        assertEquals(address, newAddress);

        verify(addressRepository, times(1)).findById(address.getId());
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    @DisplayName("Should not be able to update an address that does not exist")
    public void should_not_be_able_to_update_an_address_that_does_not_exist() {
        var idAddress = UUID.randomUUID();

        var addressDTO = AddressDTO.builder()
            .id(UUID.randomUUID())
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        when(addressRepository.findById(idAddress)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> updateAddressUseCase.execute(idAddress, addressDTO));

        verify(addressRepository, times(1)).findById(idAddress);
        verify(addressRepository, never()).save(any());
    }
}
