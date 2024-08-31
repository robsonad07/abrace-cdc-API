package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    @DisplayName("Should be able to delete a user")
    public void should_be_able_to_delete_a_user() {
        var userId = UUID.randomUUID();

        var user = User.builder()
            .id(userId)
            .name("User Test")
            .email("user@test.com")
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        this.deleteUserUseCase.execute(userId);

        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Should not be able to delete a user that does not exist")
    public void should_not_be_able_to_delete_a_user_that_does_not_exist() {
        var userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            deleteUserUseCase.execute(userId);
        });

        verify(userRepository, never()).delete(any());
    }
}
