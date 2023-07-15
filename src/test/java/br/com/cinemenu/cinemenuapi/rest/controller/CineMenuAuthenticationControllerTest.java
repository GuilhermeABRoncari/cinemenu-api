package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserResponseDto;
import br.com.cinemenu.cinemenuapi.rest.service.CineMenuUserService;
import br.com.cinemenu.cinemenuapi.rest.service.SignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CineMenuAuthenticationControllerTest {

    private CineMenuAuthenticationController authenticationController;

    @Mock
    private CineMenuUserService userService;

    @Mock
    private SignupService signupService;

    @BeforeEach
    void setup() {
        userService = Mockito.mock(CineMenuUserService.class);
        signupService = Mockito.mock(SignupService.class);
        authenticationController = new CineMenuAuthenticationController(userService, signupService);
    }

    @Test
    @DisplayName("Test signup endpoint")
    void testSignup() {
        // Given
        String name = "Name";
        String username = "Username";
        String email = "email@example.com";
        String password = "password";
        String confirmationPassword = "password";
        String id = "1L";

        CineMenuUserRequestDto requestDto = new CineMenuUserRequestDto(name, username, email, password, confirmationPassword);
        UserResponseDto responseDto = new UserResponseDto(id, username);
        when(userService.sign(requestDto)).thenReturn(responseDto);

        // When
        ResponseEntity<UserResponseDto> response = authenticationController.signup(requestDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(signupService).checkSignValidations(requestDto);
        verify(userService).sign(requestDto);
    }
}
