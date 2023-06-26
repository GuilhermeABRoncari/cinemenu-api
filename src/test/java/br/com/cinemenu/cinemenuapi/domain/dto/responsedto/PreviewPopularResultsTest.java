package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PreviewPopularResultsTest {

    @Test
    @DisplayName("Test PreviewPopularResults record")
    void testPreviewPopularResults() {
        // Given
        Integer page = 1;
        List<PreviewPopularResults.PreviewPopularResultsResponse> results = List.of(
                new PreviewPopularResults.PreviewPopularResultsResponse(
                        false, 2, 2786960L, List.of(), "Acting", "Name", 0.0, "posterPath")
        );
        Integer totalPages = 10;

        // When
        PreviewPopularResults previewPopularResults = new PreviewPopularResults(page, results, totalPages);

        // Then
        Assertions.assertEquals(page, previewPopularResults.page());
        Assertions.assertEquals(results, previewPopularResults.results());
        Assertions.assertEquals(totalPages, previewPopularResults.totalPages());
    }

    @Test
    @DisplayName("Test PreviewPopularResultsResponse record")
    void testPreviewPopularResultsResponse() {
        // Given
        Boolean adult = false;
        Integer gender = 1;
        Long id = 123456L;
        List<PreviewMediaResults.PreviewMediaResultResponse> knownFor = List.of(
                new PreviewMediaResults.PreviewMediaResultResponse(
                        false, null, 123456L, null, null, null, null,
                        null, "/path/to/profile.jpg", "person", List.of(1), 4.5,
                        null, false, 0.0, 0, "John Doe", null,
                        null, null
                )
        );
        String knownForDepartment = "Department";
        String name = "John Doe";
        Double popularity = 4.5;
        String profilePath = "/path/to/profile.jpg";

        // When
        PreviewPopularResults.PreviewPopularResultsResponse response =
                new PreviewPopularResults.PreviewPopularResultsResponse(
                        adult, gender, id, knownFor, knownForDepartment, name, popularity, profilePath
                );

        // Then
        Assertions.assertEquals(adult, response.adult());
        Assertions.assertEquals(gender, response.gender());
        Assertions.assertEquals(id, response.id());
        Assertions.assertEquals(knownFor, response.knownFor());
        Assertions.assertEquals(knownForDepartment, response.knownForDepartment());
        Assertions.assertEquals(name, response.name());
        Assertions.assertEquals(popularity, response.popularity());
        Assertions.assertEquals(profilePath, response.profilePath());
    }

}
