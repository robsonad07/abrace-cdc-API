package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

@ExtendWith(MockitoExtension.class)
public class GetAllAddressUseCaseTest {
    @InjectMocks
    private GetAllAddressUseCase getAllAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should be able to get all address")
    public void should_be_able_to_get_all_address() {
        List<Address> addressToFind = new ArrayList<>();
    
        addressToFind.add(Address.builder().build());
        addressToFind.add(Address.builder().build());

        when(addressRepository.findAll()).thenReturn(addressToFind);

        List<Address> findersAddress = getAllAddressUseCase.execute();

        assertEquals(addressToFind, findersAddress);

        verify(addressRepository, times(1)).findAll();
    }
}
