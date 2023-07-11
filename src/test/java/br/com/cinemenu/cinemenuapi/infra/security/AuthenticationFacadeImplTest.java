package br.com.cinemenu.cinemenuapi.infra.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class AuthenticationFacadeImplTest {

    @Test
    @DisplayName("Test getAuthentication")
    void testGetAuthentication() {
        // Given
        Authentication expectedAuthentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationFacadeImpl authenticationFacade = new AuthenticationFacadeImpl();

        // When
        Authentication actualAuthentication = authenticationFacade.getAuthentication();

        // Then
        Assertions.assertEquals(expectedAuthentication, actualAuthentication);
    }

}