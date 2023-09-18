package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.AccountDeleteRequestDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.CineMenuUserService;
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
    @Mock
    private CineMenuUserService service;
    private CineMenuUser validUser;
    private AccountDeleteRequestDto validAccountDeleteRequestDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationFacade = new AuthenticationFacade() {
            @Override
            public Authentication getAuthentication() {
                return authentication;
            }
        };
        controller = new UserController(repository, authenticationFacade, service);

        String id = "validId";
        String name = "name";
        String validEmail = "validEmail@example.com";
        String username = "username";
        String password = "password";
        validAccountDeleteRequestDto = new AccountDeleteRequestDto(validEmail);
        UserProfile userProfile = new UserProfile("validBiography");
        validUser = new CineMenuUser(id, userProfile,name, username, validEmail, password, OffsetDateTime.now(), false, null, List.of(new MediaList()));
    }

    @Test
    @DisplayName("Test method delete from UserController whit valid credentials")
    void testAccountDelete() {
        // Given
        when(repository.findByUsername(validUser.getUsername())).thenReturn(validUser);
        when(authenticationFacade.getAuthentication().getName()).thenReturn(validUser.getUsername());

        // When
        ResponseEntity<HttpStatus> response = controller.delete(validAccountDeleteRequestDto);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repository).findByUsername(validUser.getUsername());
    }

}