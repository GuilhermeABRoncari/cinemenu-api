package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreviewMediaResultsPageTest {

    @Test
    @DisplayName("Constructor should initialize the fields correctly")
    void testConstructor() {
        // Given
        Integer page = 1;
        List<CineMenuMediaResponse> results = List.of(
                new CineMenuMediaResponse(1, "Movie 1", "path/to/movie1.jpg", MediaType.MOVIE, "0000-00-00", 0.0),
                new CineMenuMediaResponse(2, "Movie 2", "path/to/movie2.jpg", MediaType.TV, "0000-00-00", 0.0)
        );
        Integer totalPages = 10;

        // When
        PreviewMediaResponsePage responsePage = new PreviewMediaResponsePage(page, results, totalPages);

        // Then
        assertEquals(page, responsePage.page());
        assertEquals(results, responsePage.results());
        assertEquals(totalPages, responsePage.totalPages());
    }
}
