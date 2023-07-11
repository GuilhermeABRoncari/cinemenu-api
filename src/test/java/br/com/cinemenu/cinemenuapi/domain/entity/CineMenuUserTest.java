package br.com.cinemenu.cinemenuapi.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class CineMenuUserTest {

    @Test
    @DisplayName("Test CineMenuUser entity")
    void testCineMenuUserEntity() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();

        // When
        CineMenuUser cineMenuUser = new CineMenuUser(id, name, username, email, password, registrationDate);

        // Then
        Assertions.assertEquals(id, cineMenuUser.getId());
        Assertions.assertEquals(name, cineMenuUser.getName());
        Assertions.assertEquals(username, cineMenuUser.getUsername());
        Assertions.assertEquals(email, cineMenuUser.getEmail());
        Assertions.assertEquals(password, cineMenuUser.getPassword());
        Assertions.assertEquals(registrationDate, cineMenuUser.getRegistrationDate());
        Assertions.assertTrue(cineMenuUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
        Assertions.assertTrue(cineMenuUser.isAccountNonExpired());
        Assertions.assertTrue(cineMenuUser.isAccountNonLocked());
        Assertions.assertTrue(cineMenuUser.isCredentialsNonExpired());
        Assertions.assertTrue(cineMenuUser.isEnabled());
    }

}