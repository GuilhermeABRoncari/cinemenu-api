package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.SecurityConfigurations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CineMenuUserServiceTest {

    private CineMenuUserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfigurations securityConfigurations;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        securityConfigurations = Mockito.mock(SecurityConfigurations.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new CineMenuUserService(userRepository, securityConfigurations);
        when(securityConfigurations.passwordEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    @DisplayName("Test sign method with new user")
    void testSignWithNewUser() {
        // Given
        CineMenuUserRequestDto userDto = new CineMenuUserRequestDto("name", "username", "email@example.com", "password", "password");
        when(userRepository.existsByEmail(userDto.email())).thenReturn(false);
        when(userRepository.existsByUsername(userDto.username())).thenReturn(false);
        when(passwordEncoder.encode(userDto.password())).thenReturn("encodedPassword");

        // When
        UserResponseDto responseDto = userService.sign(userDto);

        // Then
        assertNotNull(responseDto);
        assertEquals(userDto.username(), responseDto.username());
        verify(userRepository).save(any(CineMenuUser.class));
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
