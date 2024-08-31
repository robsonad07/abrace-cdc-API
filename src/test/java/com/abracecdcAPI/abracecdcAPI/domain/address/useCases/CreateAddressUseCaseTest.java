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
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressAlreadyExistException;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressWithNullParameterException;

@ExtendWith(MockitoExtension.class)
public class CreateAddressUseCaseTest{

    @InjectMocks
    private CreateAddressUseCase createAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should be able to create an address")
    public void should_be_able_to_create_an_address() {
        var id = UUID.randomUUID();

        var addressToCreate = Address.builder()
            .id(id)
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        var addressDTO = AddressDTO.builder()
            .id(id)
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        when(addressRepository.save(any(Address.class))).thenReturn(addressToCreate);

        var retrivedAddress = createAddressUseCase.execute(addressDTO);

        assertEquals(retrivedAddress, addressToCreate);

        verify(addressRepository, times(1)).save(addressToCreate);
    }

    @Test
    @DisplayName("Should not be able to create an address that already exists")
    public void should_not_be_able_to_create_an_address_that_already_exists() {
        var id = UUID.randomUUID();

        var addressToCreate = Address.builder()
            .id(id)
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        var addressDTO = AddressDTO.builder()
            .id(id)
            .city("cidade")
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        when(addressRepository.findByCityAndCepAndRoadAndNumberAndComplement(
            addressDTO.city(),
            addressDTO.cep(),
            addressDTO.road(),
            addressDTO.number(),
            addressDTO.complement()
        )).thenReturn(Optional.of(addressToCreate));

        assertThrows(AddressAlreadyExistException.class, () -> createAddressUseCase.execute(addressDTO));
   
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    @DisplayName("Should not be able to create an address with null parameter")
    public void should_not_be_able_to_create_an_address_with_null_parameter() {
        var id = UUID.randomUUID();

        var addressDTO = AddressDTO.builder()
            .id(id)
            .city(null)
            .cep("12345678")
            .road("Rua teste")
            .number(123)
            .complement("complemento")
            .build();

        assertThrows(AddressWithNullParameterException.class, () -> createAddressUseCase.execute(addressDTO));
   
        verify(addressRepository, never()).save(any(Address.class));
    }
}