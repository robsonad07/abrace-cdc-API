package com.abracecdcAPI.abracecdcAPI.domain.user.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
import com.abracecdcAPI.abracecdcAPI.infra.security.TokenService;

@ExtendWith(MockitoExtension.class)
public class LoginUserUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    @Test
    @DisplayName("Should be able to authenticate and generate token")
    public void should_be_able_to_authenticate_and_generate_token() {
    
        var usernamePassword = new UsernamePasswordAuthenticationToken("user@test.com", "password");
        var user = new User();
        user.setEmail("user@test.com");
        
        var auth = new UsernamePasswordAuthenticationToken(user, "password");

        when(authenticationManager.authenticate(usernamePassword)).thenReturn(auth);
        when(tokenService.generateToken((User) auth.getPrincipal())).thenReturn("mocked-jwt-token");

        String token = loginUserUseCase.execute(usernamePassword);

        assertEquals("mocked-jwt-token", token);
        verify(authenticationManager).authenticate(usernamePassword);
        verify(tokenService).generateToken((User) auth.getPrincipal());
    }

    @Test
    @DisplayName("Should not be able to authenticate for invalid credentials")
    public void should_not_be_able_to_authenticate_for_invalid_credentials() {
        
        var usernamePassword = new UsernamePasswordAuthenticationToken("user@test.com", "wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

        
        assertThrows(BadCredentialsException.class, () -> {
            loginUserUseCase.execute(usernamePassword);
        });

        verify(authenticationManager).authenticate(usernamePassword);
        verify(tokenService, never()).generateToken(any(User.class));
    }
}
