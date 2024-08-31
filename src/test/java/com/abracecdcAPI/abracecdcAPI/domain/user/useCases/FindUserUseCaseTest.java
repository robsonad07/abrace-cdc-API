package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
public class FindUserUseCaseTest {

    @InjectMocks
    private FindUserUseCase findUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to find a user by ID")
    public void should_be_able_to_find_a_user_by_id() {
        var userId = UUID.randomUUID();

        var user = User.builder()
            .id(userId)
            .name("Test User")
            .email("testuser@test.com")
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var result = findUserUseCase.execute(userId);

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Should not be able to find user when user does not exist")
    public void should_not_be_able_to_find_user_when_user_does_not_exist() {
        var userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            findUserUseCase.execute(userId);
        });
    }
}
