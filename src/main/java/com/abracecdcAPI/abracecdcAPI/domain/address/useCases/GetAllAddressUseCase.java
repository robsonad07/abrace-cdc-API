package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllAddressUseCase {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> execute(){
        return addressRepository.findAll();
    }
}
