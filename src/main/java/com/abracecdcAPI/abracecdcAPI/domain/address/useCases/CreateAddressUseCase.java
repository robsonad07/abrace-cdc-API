package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.address.dto.AddressDTO;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateAddressUseCase {
    @Autowired
    private AddressRepository addressRepository;

    public Address execute(AddressDTO addressDTO){
        Address newAddress = new Address(addressDTO.city(), addressDTO.cep(), addressDTO.road(), addressDTO.number(), addressDTO.complement());
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
