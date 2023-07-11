package br.com.cinemenu.cinemenuapi.infra.security;

import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    public AuthenticationServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test loadUserByUsername when user exists")
    void testLoadUserByUsernameWhenUserExists() {
        // Given
        String username = "testUser";
        UserDetails expectedUser = mock(UserDetails.class);

        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        // When
        UserDetails actualUser = authenticationService.loadUserByUsername(username);

        // Then
        Assertions.assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Test loadUserByUsername when user does not exist")
    void testLoadUserByUsernameWhenUserDoesNotExist() {
        // Given
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        // When/Then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.loadUserByUsername(username);
        });

        verify(userRepository, times(1)).findByUsername(username);
    }

}