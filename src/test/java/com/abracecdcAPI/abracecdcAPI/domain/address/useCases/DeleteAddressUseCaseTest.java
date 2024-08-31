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

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;

@ExtendWith(MockitoExtension.class)
public class DeleteAddressUseCaseTest {
    
    @InjectMocks
    private DeleteAddressUseCase deleteAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should be able to delete an address")
    public void should_be_able_to_delete_an_address() {
        var addressId = UUID.randomUUID();

        var address = Address.builder()
            .id(addressId)
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        var retrivedAddress = deleteAddressUseCase.execute(addressId);

        assertEquals(retrivedAddress, "Address deleted successfully.");

        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
    @DisplayName("Should not be able to delete an address that does not exist")
    public void should_not_be_able_to_delete_an_address_that_does_not_exist() {
        var addressId = UUID.randomUUID();

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> {
            deleteAddressUseCase.execute(addressId);
        });

        verify(addressRepository, never()).delete(any());
    }
}
