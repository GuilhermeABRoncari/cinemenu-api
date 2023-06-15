package br.com.cinemenu.cinemenuapi.domain.enumeration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CineMenuGenresTest {

    @Test
    @DisplayName("fromId should return the corresponding genre for a valid genre ID")
    void testFromId_ValidGenreId() {
        // Given
        int genreId = 32; // Comedy

        // When
        CineMenuGenres genre = CineMenuGenres.fromId(genreId);

        // Then
        assertEquals(CineMenuGenres.COMEDY, genre);
    }

    @Test
    @DisplayName("fromId should throw an exception for an invalid genre ID")
    void testFromId_InvalidGenreId() {
        // Given
        int genreId = 999; // Invalid genre ID

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> CineMenuGenres.fromId(genreId));
    }
}
