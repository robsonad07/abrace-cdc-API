package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import com.abracecdcAPI.abracecdcAPI.domain.user.dto.UserRecordDTO;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.UserRole;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAdminUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateAdminUseCase updateAdminUseCase;

    @Test
    @DisplayName("Should be able to update an existing user")
    public void should_be_able_to_update_an_existing_user() {

        var userId = UUID.randomUUID();
        var passwordEncoder = new BCryptPasswordEncoder().encode("password");
        var existingUser = User.builder()
                .name("User Name")
                .email("admin@email.com")
                .password(passwordEncoder)
                .phone("999999999")
                .role(UserRole.ADMIN)
                .build();

        var userRecordDTO = UserRecordDTO.builder()
                .name("User Name")
                .email("new-admin@email.com")
                .password("new-password")
                .phone("888888888")
                .role(UserRole.ADMIN)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));  

        User updatedUser = updateAdminUseCase.execute(userId, userRecordDTO);

        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        assertEquals("User Name", updatedUser.getName());
        assertEquals("new-admin@email.com", updatedUser.getEmail());
        assertTrue(new BCryptPasswordEncoder().matches(userRecordDTO.password(), updatedUser.getPassword())); 
        assertEquals("888888888", updatedUser.getPhone());
        assertEquals(UserRole.ADMIN, updatedUser.getRole());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void execute_whenUserDoesNotExist_shouldThrowUserNotFoundException() {

        var userId = UUID.randomUUID();

        var userRecordDTO = UserRecordDTO.builder()
        .name("User Name")
        .email("new-admin@email.com")
        .password("new-password")
        .phone("888888888")
        .role(UserRole.ADMIN)
        .build();
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            updateAdminUseCase.execute(userId, userRecordDTO);
        });

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }
}
