package com.abracecdcAPI.abracecdcAPI.domain.register.repository;

import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegisterRepository extends JpaRepository<Register, UUID> {
    Optional<Register> findByUrlImage(String url_image);
}