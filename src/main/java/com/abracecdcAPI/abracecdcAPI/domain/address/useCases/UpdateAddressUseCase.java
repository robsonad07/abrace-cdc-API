package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.address.dto.AddressDTO;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;

@Service
public class UpdateAddressUseCase {
    @Autowired
    AddressRepository addressRepository;

    public Address execute(UUID id, AddressDTO addressDTO){
        Optional<Address> address = addressRepository.findById(id);
        if(address.isEmpty()){
            throw new AddressNotFoundException();
        }
        Address newAddress = new Address(id, addressDTO.city(), addressDTO.cep(), addressDTO.road(), addressDTO.number(), addressDTO.complement());
        addressRepository.save(newAddress);
        return newAddress;
    }
}
