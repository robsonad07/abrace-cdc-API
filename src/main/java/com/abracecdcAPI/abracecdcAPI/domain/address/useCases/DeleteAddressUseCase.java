package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteAddressUseCase {
    @Autowired
    AddressRepository addressRepository;

    public String execute(UUID id){
        Optional<Address> address = addressRepository.findById(id);
        if(address.isEmpty()){
            throw new AddressNotFoundException();
        }
        addressRepository.deleteById(id);
        return "Address deleted successfully.";
    }
}
