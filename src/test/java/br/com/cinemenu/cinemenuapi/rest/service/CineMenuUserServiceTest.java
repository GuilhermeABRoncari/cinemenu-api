package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.LoginRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.TokenResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.SecurityConfigurations;
import br.com.cinemenu.cinemenuapi.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CineMenuUserServiceTest {

    private CineMenuUserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfigurations securityConfigurations;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        securityConfigurations = Mockito.mock(SecurityConfigurations.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        tokenService = Mockito.mock(TokenService.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        userService = new CineMenuUserService(userRepository, securityConfigurations, tokenService, authenticationManager);
        when(securityConfigurations.passwordEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    @DisplayName("Test login with valid credentials")
    void testLoginWithValidCredentialsShouldReturnTokenResponseDto() throws AuthenticationException {
        // Given
        LoginRequestDto loginDto = new LoginRequestDto("email@example.com", "Test123*");
        String token = "valid_token";
        CineMenuUser user = new CineMenuUser(null, "name", "teste", "email@example.com", "Test123*", null);
        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(user)).thenReturn(token);
        when(userRepository.getReferenceByEmail(loginDto.email())).thenReturn(user);

        // When
        var response = userService.login(loginDto);

        // Then
        assertNotNull(response);
        assertEquals(token, response.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(user);
        verify(userRepository).getReferenceByEmail(loginDto.email());
    }

    @Test
    @DisplayName("Test login with invalid credentials")
    void testLoginWithInvalidCredentialsShouldThrowIllegalArgumentException() {
        // Given
        LoginRequestDto loginDto = new LoginRequestDto("email@example.com", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(IllegalArgumentException.class);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(loginDto));
    }

    @Test
    @DisplayName("Test sign method with existing email")
    void testSignWithExistingEmail() {
        // Given
        CineMenuUserRequestDto userDto = new CineMenuUserRequestDto("name", "username", "email@example.com", "password", "password");
        when(userRepository.existsByEmail(userDto.email())).thenReturn(true);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.sign(userDto));
        verify(userRepository, never()).existsByUsername(anyString());
        verify(userRepository, never()).save(any(CineMenuUser.class));
    }

    @Test
    @DisplayName("Test sign method with existing username")
    void testSignWithExistingUsername() {
        // Given
        CineMenuUserRequestDto userDto = new CineMenuUserRequestDto("name", "username", "email@example.com", "password", "password");
        when(userRepository.existsByEmail(userDto.email())).thenReturn(false);
        when(userRepository.existsByUsername(userDto.username())).thenReturn(true);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.sign(userDto));
        verify(userRepository).existsByEmail(anyString());
        verify(userRepository, never()).save(any(CineMenuUser.class));
    }
}
