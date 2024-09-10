package com.abracecdcAPI.abracecdcAPI.domain.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
