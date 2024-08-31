package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.abracecdcAPI.abracecdcAPI.domain.user.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.UserRole;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {
    
    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to register a new user with encrypted password")
    public void should_be_able_to_register_a_new_user_with_encrypted_password() {
        
        var passwordEncoder = new BCryptPasswordEncoder().encode("password123");

        var userToCreate = User.builder()
                .name("User")
                .email("user@email.com")
                .password(passwordEncoder)
                .phone("123456789")
                .role(UserRole.USER)
                .build();

        var data = RegisterDTO.builder()
                .name("User")
                .email("user@email.com")
                .password("password123")
                .phone("123456789")
                .role(UserRole.USER)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(userToCreate); 
        
        User registeredUser = registerUserUseCase.execute(data);

        assertEquals(registeredUser, userToCreate);
        assertTrue(new BCryptPasswordEncoder().matches(data.password(), registeredUser.getPassword()));
    }

    @Test
    @DisplayName("Should not be able to register a new user with an already registered email")
    public void should_not_be_able_to_register_a_new_user_with_an_already_registered_email() {
        
        var data = RegisterDTO.builder()
                .name("User")
                .email("user@email.test")
                .password("password123")
                .phone("123456789")
                .role(UserRole.USER)
                .build();

        when(userRepository.findByEmail(data.email())).thenReturn(new User());

        User registeredUser = registerUserUseCase.execute(data);

        assertEquals(registeredUser, null);
    }
}
