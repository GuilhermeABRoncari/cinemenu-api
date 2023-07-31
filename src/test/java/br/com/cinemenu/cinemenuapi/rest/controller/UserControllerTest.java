package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.AccountDeleteRequestDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WithMockUser
@AllArgsConstructor
class UserControllerTest {

    private UserController controller;
    @Mock
    private UserRepository repository;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationFacade = new AuthenticationFacade() {
            @Override
            public Authentication getAuthentication() {
                return authentication;
            }
        };
        controller = new UserController(repository, authenticationFacade);
    }

    @Test
    @DisplayName("Test method delete from UserController whit valid credentials")
    void testAccountDelete() {
        // Given
        String id = "validId";
        String name = "name";
        String validEmail = "validEmail@example.com";
        String username = "username";
        String password = "password";
        AccountDeleteRequestDto dto = new AccountDeleteRequestDto(validEmail);
        CineMenuUser validUser = new CineMenuUser(id, name, username, validEmail, password, OffsetDateTime.now(), false, null, List.of(new MediaList()));

        when(repository.findByUsername(username)).thenReturn(validUser);
        when(authenticationFacade.getAuthentication().getName()).thenReturn(username);

        // When
        ResponseEntity<HttpStatus> response = controller.delete(dto);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repository).findByUsername(username);
    }

    @Test
    @DisplayName("Test method delete from UserController whit invalid credentials")
    void testAccountDelete02() {
        // Given
        String id = "validId";
        String name = "name";
        String invalidEmail = "InvalidEmail@example.com";
        String email = "email@example.com";
        String username = "username";
        String password = "password";
        AccountDeleteRequestDto dto = new AccountDeleteRequestDto(invalidEmail);
        CineMenuUser validUser = new CineMenuUser(id, name, username, email, password, OffsetDateTime.now(), false, null, List.of(new MediaList()));

        when(repository.findByUsername(username)).thenReturn(validUser);
        when(authenticationFacade.getAuthentication().getName()).thenReturn(username);

        // When // Then
        assertThrows(IllegalArgumentException.class, () -> {
            ResponseEntity<HttpStatus> response = controller.delete(dto);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        });

    }

}