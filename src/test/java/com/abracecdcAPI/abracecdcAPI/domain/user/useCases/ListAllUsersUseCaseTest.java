package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ListAllUsersUseCaseTest {

    @InjectMocks
    private ListAllUsersUseCase listAllUsersUseCase;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to return a list of all users")
    public void should_be_able_to_return_a_list_of_all_users() {
        var user1 = User.builder()
            .id(UUID.randomUUID())
            .name("User One")
            .email("user1@example.com")
            .build();

        var user2 = User.builder()
            .id(UUID.randomUUID())
            .name("User Two")
            .email("user2@example.com")
            .build();

        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> result = listAllUsersUseCase.execute();

        assertEquals(expectedUsers, result);
    }

    @Test
    @DisplayName("Should be able to return an empty list if no users are found")
    public void should__be_able_to_return_empty_list_if_no_users_are_found() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<User> result = listAllUsersUseCase.execute();

        assertEquals(0, result.size());
    }
}
