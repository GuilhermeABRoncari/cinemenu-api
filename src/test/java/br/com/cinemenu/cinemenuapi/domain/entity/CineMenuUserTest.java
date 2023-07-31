package br.com.cinemenu.cinemenuapi.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

class CineMenuUserTest {

    @Test
    @DisplayName("Test CineMenuUser entity")
    void testCineMenuUserEntity() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();

        // When
        CineMenuUser cineMenuUser = new CineMenuUser(id, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

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

    @Test
    @DisplayName("Test CineMenuUser entity EqualsAndHashcode of id")
    void testCineMenuUserEntity02() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();

        // When
        CineMenuUser cineMenuUser01 = new CineMenuUser(id, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        CineMenuUser cineMenuUser02 = new CineMenuUser(id, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        int hashCode = cineMenuUser02.hashCode();
        boolean equals = cineMenuUser01.equals(cineMenuUser02);

        // Then
        Assertions.assertEquals(hashCode, cineMenuUser02.hashCode());
        Assertions.assertTrue(equals);
    }

    @Test
    @DisplayName("Test CineMenuUser entity EqualsAndHashcode of id")
    void testCineMenuUserEntity03() {
        // Given
        String firstId = "1L";
        String secondId = "2L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();

        // When
        CineMenuUser cineMenuUser01 = new CineMenuUser(firstId, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        CineMenuUser cineMenuUser02 = new CineMenuUser(secondId, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        boolean equals = cineMenuUser01.equals(cineMenuUser02);

        // Then
        Assertions.assertFalse(equals);
    }
}