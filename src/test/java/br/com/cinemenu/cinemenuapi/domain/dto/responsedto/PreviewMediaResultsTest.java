package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PreviewMediaResultsTest {

    @Test
    @DisplayName("Test creating PreviewMediaResponse")
    void createPreviewMediaResponse() {
        // Given
        int page = 1;
        List<PreviewMediaResults.PreviewMediaResultResponse> results = Arrays.asList(
                new PreviewMediaResults.PreviewMediaResultResponse(
                        false, "backdrop_path", 1L, "title", "pt-br", "original_title", "overview",
                        "poster_path", "profile_path", "movie", Arrays.asList(1, 2), 5.0, "2023-05-24",
                        false, 7.5, 100, null, null, null, Arrays.asList("BR")
                )
        );
        int totalPages = 2;

        // When
        PreviewMediaResults previewMediaResults = new PreviewMediaResults(page, results, totalPages);

        // Then
        Assertions.assertEquals(page, previewMediaResults.page());
        Assertions.assertEquals(results, previewMediaResults.results());
        Assertions.assertEquals(totalPages, previewMediaResults.total_pages());

        // Verify attributes of PreviewMediaResultResponse
        PreviewMediaResults.PreviewMediaResultResponse result = results.get(0);
        Assertions.assertEquals(false, result.adult());
        Assertions.assertEquals("backdrop_path", result.backdrop_path());
        Assertions.assertEquals(1, result.id());
        Assertions.assertEquals("title", result.title());
        Assertions.assertEquals("pt-br", result.original_language());
        Assertions.assertEquals("original_title", result.original_title());
        Assertions.assertEquals("overview", result.overview());
        Assertions.assertEquals("poster_path", result.poster_path());
        Assertions.assertEquals("profile_path", result.profile_path());
        Assertions.assertEquals("movie", result.media_type());
        Assertions.assertEquals(Arrays.asList(1, 2), result.genre_ids());
        Assertions.assertEquals(5.0, result.popularity());
        Assertions.assertEquals("2023-05-24", result.release_date());
        Assertions.assertEquals(false, result.video());
        Assertions.assertEquals(7.5, result.vote_average());
        Assertions.assertEquals(100, result.vote_count());
        Assertions.assertNull(result.original_name());
        Assertions.assertNull(result.first_air_date());
        Assertions.assertEquals(Arrays.asList("BR"), result.origin_country());
    }
}
