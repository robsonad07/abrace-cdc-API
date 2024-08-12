package com.abracecdcAPI.abracecdcAPI.domain.registro.repository;

import com.abracecdcAPI.abracecdcAPI.domain.registro.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegisterRepository extends JpaRepository<Register, UUID> {
}
