package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

class UserResponseDtoTest {

    @Test
    @DisplayName("Test UserResponseDto constructor")
    void testUserResponseDtoConstructor() {
        // Given
        String id = "1L";
        String username = "johndoe";

        // When
        UserResponseDto responseDto = new UserResponseDto(id, username);

        // Then
        Assertions.assertEquals(id, responseDto.id());
        Assertions.assertEquals(username, responseDto.username());
    }

    @Test
    @DisplayName("Test UserResponseDto constructor with CineMenuUser")
    void testUserResponseDtoConstructorWithCineMenuUser() {
        // Given
        String id = "1L";
        String username = "johndoe";
        String name = "John Doe";
        UserProfile userProfile = new UserProfile("bio");
        CineMenuUser cineMenuUser = new CineMenuUser(id, userProfile, name, username, "johndoe@example.com", "password", OffsetDateTime.now(), false, null, List.of(new MediaList()));

        // When
        UserResponseDto responseDto = new UserResponseDto(cineMenuUser);

        // Then
        Assertions.assertEquals(id, responseDto.id());
        Assertions.assertEquals(username, responseDto.username());
    }

}