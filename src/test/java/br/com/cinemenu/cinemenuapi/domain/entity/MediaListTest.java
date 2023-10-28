package br.com.cinemenu.cinemenuapi.domain.entity;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MediaListTest {

    private MediaList mediaList;
    private CineMenuUser user;

    @BeforeEach
    void setUp() {
        // Configuração inicial antes de cada teste
        user = new CineMenuUser();
        mediaList = new MediaList(new MediaListRequestDto("Test Title", "Test Description", ListVisibility.PUBLIC), user);
    }

    @Test
    @DisplayName("Test MediaList constructor")
    void testMediaListCreation() {
        assertEquals("Test Title", mediaList.getTitle());
        assertEquals("Test Description", mediaList.getDescription());
        assertEquals(ListVisibility.PUBLIC, mediaList.getVisibility());
        assertEquals(Integer.valueOf(0), mediaList.getAmountLike());
        assertEquals(Integer.valueOf(0), mediaList.getAmountCopy());
        assertEquals(user, mediaList.getUser());
    }

    @Test
    @DisplayName("Test update method")
    void testMediaListUpdate() {
        // Given
        MediaListRequestDto updateDto = new MediaListRequestDto("Updated Title", "Updated Description", ListVisibility.PRIVATE);

        // When
        mediaList.update(updateDto);

        // Then
        assertEquals("Updated Title", mediaList.getTitle());
        assertEquals("Updated Description", mediaList.getDescription());
        assertEquals(ListVisibility.PRIVATE, mediaList.getVisibility());
    }

    @Test
    @DisplayName("Test incrementCopyCounter method")
    void testIncrementCopyCounter() {
        // When
        mediaList.incrementCopyCounter();
        // Then
        assertEquals(Integer.valueOf(1), mediaList.getAmountCopy());

        // When
        mediaList.incrementCopyCounter();
        // Then
        assertEquals(Integer.valueOf(2), mediaList.getAmountCopy());
    }
}