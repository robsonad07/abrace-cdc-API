package com.abracecdcAPI.abracecdcAPI.domain.address.repository;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByCityAndCepAndRoadAndNumberAndComplement(
            String city, String cep, String road, int number, String complement);
}
