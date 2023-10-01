package br.com.cinemenu.cinemenuapi.domain.entity;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        UserProfile userProfile = new UserProfile("bio");

        // When
        CineMenuUser cineMenuUser = new CineMenuUser(id, userProfile,name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        // Then
        assertEquals(id, cineMenuUser.getId());
        assertEquals(userProfile, cineMenuUser.getProfile());
        assertEquals(name, cineMenuUser.getName());
        assertEquals(username, cineMenuUser.getUsername());
        assertEquals(email, cineMenuUser.getEmail());
        assertEquals(password, cineMenuUser.getPassword());
        assertEquals(registrationDate, cineMenuUser.getRegistrationDate());
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
        UserProfile userProfile = new UserProfile("bio");

        // When
        CineMenuUser cineMenuUser01 = new CineMenuUser(id, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        CineMenuUser cineMenuUser02 = new CineMenuUser(id, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        int hashCode = cineMenuUser02.hashCode();
        boolean equals = cineMenuUser01.equals(cineMenuUser02);

        // Then
        assertEquals(hashCode, cineMenuUser02.hashCode());
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
        UserProfile userProfile = new UserProfile("bio");

        // When
        CineMenuUser cineMenuUser01 = new CineMenuUser(firstId, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        CineMenuUser cineMenuUser02 = new CineMenuUser(secondId, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        boolean equals = cineMenuUser01.equals(cineMenuUser02);

        // Then
        Assertions.assertFalse(equals);
    }

    @Test
    @DisplayName("")
    void testUpdate() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();

        var userProfileRequest = new UserProfileRequestDto("new name", "new username", "new bio");
        CineMenuUser cineMenuUser = new CineMenuUser(id, new UserProfile("bio"),name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        // When
        cineMenuUser.updateProfile(userProfileRequest);

        // Then
        assertEquals("new name", cineMenuUser.getName());
        assertEquals("new username", cineMenuUser.getUsername());
        assertEquals("new bio", cineMenuUser.getProfile().getBiography());
    }
}