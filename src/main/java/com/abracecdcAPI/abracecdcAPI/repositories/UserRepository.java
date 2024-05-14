package com.abracecdcAPI.abracecdcAPI.repositories;

import com.abracecdcAPI.abracecdcAPI.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
