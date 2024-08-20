package com.abracecdcAPI.abracecdcAPI.domain.user.repository;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}
