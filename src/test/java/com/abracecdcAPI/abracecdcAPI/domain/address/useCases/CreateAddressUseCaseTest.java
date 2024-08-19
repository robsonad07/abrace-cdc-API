package com.abracecdcAPI.abracecdcAPI.domain.address.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.addressEntity;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.addressRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.CellphoneAlreadyExistsException;
import com.abracecdcAPI.abracecdcAPI.exceptions.EmailAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class CreateAddressUseCaseTest {


}