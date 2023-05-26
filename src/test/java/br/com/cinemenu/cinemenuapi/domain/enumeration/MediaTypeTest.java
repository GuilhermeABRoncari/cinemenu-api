package br.com.cinemenu.cinemenuapi.domain.enumeration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MediaTypeTest {

    @Test
    @DisplayName("Test converting string to MediaType: TV")
    void fromString_tv() {
        // Given
        String mediaTypeString = "TV";

        // When
        MediaType mediaType = MediaType.fromString(mediaTypeString);

        // Then
        Assertions.assertEquals(MediaType.TV, mediaType);
    }

    @Test
    @DisplayName("Test converting string to MediaType: MOVIE")
    void fromString_movie() {
        // Given
        String mediaTypeString = "MOVIE";

        // When
        MediaType mediaType = MediaType.fromString(mediaTypeString);

        // Then
        Assertions.assertEquals(MediaType.MOVIE, mediaType);
    }

    @Test
    @DisplayName("Test converting string to MediaType: PERSON")
    void fromString_person() {
        // Given
        String mediaTypeString = "PERSON";

        // When
        MediaType mediaType = MediaType.fromString(mediaTypeString);

        // Then
        Assertions.assertEquals(MediaType.PERSON, mediaType);
    }

    @Test
    @DisplayName("Test converting invalid string to MediaType")
    void fromString_invalid() {
        // Given
        String mediaTypeString = "INVALID";

        // When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MediaType.fromString(mediaTypeString);
        });
    }

    @Test
    @DisplayName("Test converting null string to MediaType")
    void fromString_null() {
        // Given
        String mediaTypeString = null;

        // When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MediaType.fromString(mediaTypeString);
        });
    }

    @Test
    @DisplayName("Test converting blank string to MediaType")
    void fromString_blank() {
        // Given
        String mediaTypeString = "";

        // When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MediaType.fromString(mediaTypeString);
        });
    }
}
