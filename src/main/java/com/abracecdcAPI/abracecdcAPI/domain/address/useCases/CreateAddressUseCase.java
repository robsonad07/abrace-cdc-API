package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.address.dto.AddressDTO;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressAlreadyExistException;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressWithNullParameterException;

@Service
public class CreateAddressUseCase {
    @Autowired
    private AddressRepository addressRepository;

    public Address execute(AddressDTO addressDTO){
        Address newAddress = new Address(addressDTO.id(), addressDTO.city(), addressDTO.cep(), addressDTO.road(), addressDTO.number(), addressDTO.complement());

        if(newAddress.getCity() == null || 
           newAddress.getCep() == null || 
           newAddress.getRoad() == null || 
           newAddress.getNumber() == 0 || 
           newAddress.getComplement() == null) {
            throw new AddressWithNullParameterException();
        }
        
        Optional<Address> existingAddress = addressRepository.findByCityAndCepAndRoadAndNumberAndComplement(
                newAddress.getCity(),
                newAddress.getCep(),
                newAddress.getRoad(),
                newAddress.getNumber(),
                newAddress.getComplement()
        );

        if(existingAddress.isPresent()){
            throw new AddressAlreadyExistException();
        }

        addressRepository.save(newAddress);

        return newAddress;
    }

}
