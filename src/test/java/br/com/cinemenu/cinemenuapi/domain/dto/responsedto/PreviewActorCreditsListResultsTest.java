package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PreviewActorCreditsListResultsTest {

    @Test
    @DisplayName("Test PreviewActorCreditsListResults record")
    void testPreviewActorCreditsListResults() {
        // Given
        List<PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse> results = List.of(
                new PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse(
                        false, "/path/to/backdrop.jpg", List.of(1, 2, 3), 123456L, "Original Name",
                        "Original Title", "Name", "Title", "pt-BR", "Overview", 0.0,
                        "/path/to/poster.jpg", "0000-00-00", "0000-00-00", false, 0.0, 0,
                        "Character", "Credits", 0, 0
                        )
        );
        Long id = 123456L;

        // When
        PreviewActorCreditsListResults response = new PreviewActorCreditsListResults(results, id);

        // Then
        Assertions.assertEquals(results, response.results());
        Assertions.assertEquals(id, response.id());
    }

    @Test
    @DisplayName("Test PreviewActorCreditsListResultsResponse record")
    void testPreviewActorCreditsListResultsResponse() {
        // Given
        Boolean adult = true;
        String backdropPath = "/path/to/backdrop.jpg";
        List<Integer> genreIds = List.of(1, 2, 3);
        Long id = 123456L;
        String originalName = "Original Name";
        String originalTitle = "Original Title";
        String name = "Name";
        String title = "Title";
        String originalLanguage = "en";
        String overview = "Overview";
        Double popularity = 4.5;
        String posterPath = "/path/to/poster.jpg";
        String releaseDate = "2023-01-01";
        String firstAirDate = "2023-01-01";
        Boolean video = false;
        Double voteAverage = 7.8;
        Integer voteCount = 100;
        String character = "Character";
        String creditId = "ABC123";
        Integer episodeCount = 10;
        Integer order = 1;

        // When
        PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse response =
                new PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse(
                        adult, backdropPath, genreIds, id, originalName, originalTitle, name, title,
                        originalLanguage, overview, popularity, posterPath, releaseDate, firstAirDate,
                        video, voteAverage, voteCount, character, creditId, episodeCount, order
                );

        // Then
        Assertions.assertEquals(adult, response.adult());
        Assertions.assertEquals(backdropPath, response.backdropPath());
        Assertions.assertEquals(genreIds, response.genreIds());
        Assertions.assertEquals(id, response.id());
        Assertions.assertEquals(originalName, response.originalName());
        Assertions.assertEquals(originalTitle, response.originalTitle());
        Assertions.assertEquals(name, response.name());
        Assertions.assertEquals(title, response.title());
        Assertions.assertEquals(originalLanguage, response.originalLanguage());
        Assertions.assertEquals(overview, response.overview());
        Assertions.assertEquals(popularity, response.popularity());
        Assertions.assertEquals(posterPath, response.posterPath());
        Assertions.assertEquals(releaseDate, response.releaseDate());
        Assertions.assertEquals(firstAirDate, response.firstAirDate());
        Assertions.assertEquals(video, response.video());
        Assertions.assertEquals(voteAverage, response.voteAverage());
        Assertions.assertEquals(voteCount, response.voteCount());
        Assertions.assertEquals(character, response.character());
        Assertions.assertEquals(creditId, response.creditId());
        Assertions.assertEquals(episodeCount, response.episodeCount());
        Assertions.assertEquals(order, response.order());
    }

}