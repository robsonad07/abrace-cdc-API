package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abracecdcAPI.abracecdcAPI.domain.user.dto.RegisterDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.UserRole;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class RegisterAdminUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterAdminUseCase registerAdminUseCase;

    @Test
    @DisplayName("Should be able to register a new admin user with encrypted password")
    public void should_be_able_to_register_a_new_admin_user_with_encrypted_password() {
       
        var passwordEncoder = new BCryptPasswordEncoder().encode("password123");

        var userToCreate = User.builder()
                .name("Admin User")
                .email("admin@email.com")
                .password(passwordEncoder)
                .phone("123456789")
                .role(UserRole.ADMIN)
                .build();

        var data = RegisterDTO.builder()
                .name("Admin User")
                .email("admin@email.com")
                .password("password123")
                .phone("123456789")
                .role(UserRole.ADMIN)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(userToCreate);

        User registeredUser = registerAdminUseCase.execute(data);


        assertEquals(registeredUser, userToCreate);
    }

    @Test
    @DisplayName("Should not register a user if email already exists")
    public void should_not_register_user_if_email_already_exists() {

        var data = RegisterDTO.builder()
                .name("Admin User")
                .email("admin@email.com")
                .password("password123")
                .phone("123456789")
                .role(UserRole.ADMIN)
                .build();

        when(userRepository.findByEmail(data.email())).thenReturn(new User());

        User registeredUser = registerAdminUseCase.execute(data);

        assertNull(registeredUser);
        verify(userRepository, never()).save(any(User.class));
    }
}
