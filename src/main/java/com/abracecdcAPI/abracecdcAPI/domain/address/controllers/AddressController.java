package com.abracecdcAPI.abracecdcAPI.domain.address.controllers;

import com.abracecdcAPI.abracecdcAPI.domain.address.dto.AddressDTO;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.useCases.CreateAddressUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.address.useCases.FindAddressUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.address.useCases.GetAllAddressUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AddressController {

    @Autowired
    CreateAddressUseCase createAddressUseCase;
    @Autowired
    GetAllAddressUseCase getAllAddressUseCase;
    @Autowired
    FindAddressUseCase findAddressUseCase;

    @PostMapping("/address")
    public ResponseEntity<Object> createAddress(@RequestBody AddressDTO addressDTO){
        try {
            Address address = createAddressUseCase.execute(addressDTO);
            return ResponseEntity.status(HttpStatus.OK).body(address);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/address")
    public ResponseEntity<Object> getAllAddress(){
        try {
            List<Address> address = getAllAddressUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(address);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Object> getOneAddress(@PathVariable(value = "id") UUID id){
        try {
            Address address = findAddressUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(address);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
