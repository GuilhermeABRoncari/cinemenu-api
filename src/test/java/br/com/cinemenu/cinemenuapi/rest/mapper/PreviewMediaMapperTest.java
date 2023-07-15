package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewActorCreditsListResults;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResults;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewPopularResults;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PreviewMediaMapperTest {

    @Test
    @DisplayName("Test mapping from PreviewMediaResponse to CineMenuMediaResponse whit MediaType.MOVIE")
    void map_previewMediaResponseToCineMenuMediaResponseWhitMOVE() {
        List<Integer> genderIDs = new ArrayList<>();
        genderIDs.addAll(List.of(16, 12, 35, 10751));
        // Given
        var previewMediaResultResponse = new PreviewMediaResults.PreviewMediaResultResponse(
                false, "/4mlS1MitcOqdPLhxEFyk9Qwf5rr.jpg", 260514L, "Carros 3", "en", "Cars 3",
                "Durante mais uma disputa eletrizante nas pistas, o campeão Relâmpago McQueen acelerou demais e acabou perdendo o controle. Agora, após ter capotando várias vezes e quase ter partido dessa para melhor, o vermelhinho vai ter sua vida alterada para sempre. O acidente foi tão grave que, com os estragos, McQueen pode ter que se aposentar de vez.",
                "/lVPFdORefTKXKFiSktrXbiFDNAK.jpg", null, "movie", genderIDs, 74.18, "2017-06-15",
                false, 6.857, 5080, null, null, null, null
        );

        // When
        CineMenuMediaResponse cineMenuMediaResponse = PreviewMediaMapper.genericMediaMap(previewMediaResultResponse);

        // Then
        assertEquals(260514, cineMenuMediaResponse.id());
        assertEquals("Carros 3", cineMenuMediaResponse.title());
        assertEquals("/lVPFdORefTKXKFiSktrXbiFDNAK.jpg", cineMenuMediaResponse.poster_path());
        assertEquals(MediaType.MOVIE, cineMenuMediaResponse.media_type());
        assertEquals("2017-06-15", cineMenuMediaResponse.release_date());
        assertEquals(6.857, cineMenuMediaResponse.vote_average());
    }

    @Test
    @DisplayName("Test mapping from PreviewMediaResponse to CineMenuMediaResponse whit MediaType.TV")
    void map_previewMediaResponseToCineMenuMediaResponseWhitTV() {
        List<Integer> genderIDs = new ArrayList<>();
        genderIDs.addAll(List.of(18, 80));
        List<String> originCountry = new ArrayList<>();
        originCountry.add("US");
        // Given
        var previewMediaResultResponse = new PreviewMediaResults.PreviewMediaResultResponse(
                false, "/84XPpjGvxNyExjSuLQe0SzioErt.jpg", 1396L, null, "en", null,
                "Ao saber que tem câncer, um professor passa a fabricar metanfetamina pelo futuro da família, mudando o destino de todos.",
                "/30erzlzIOtOK3k3T3BAl1GiVMP1.jpg", null, "tv", genderIDs, 309.338, null,
                false, 8.88, 11704, "Breaking Bad: A Química do Mal", "Breaking Bad", "2008-01-20", originCountry
        );

        // When
        CineMenuMediaResponse cineMenuMediaResponse = PreviewMediaMapper.genericMediaMap(previewMediaResultResponse);

        // Then
        assertEquals(1396, cineMenuMediaResponse.id());
        assertEquals("Breaking Bad: A Química do Mal", cineMenuMediaResponse.title());
        assertEquals("/30erzlzIOtOK3k3T3BAl1GiVMP1.jpg", cineMenuMediaResponse.poster_path());
        assertEquals(MediaType.TV, cineMenuMediaResponse.media_type());
        assertEquals("2008-01-20", cineMenuMediaResponse.release_date());
        assertEquals(8.88, cineMenuMediaResponse.vote_average());
    }

    @Test
    @DisplayName("Test mapping from PreviewMediaResponse to CineMenuMediaResponse whit MediaType.PERSON")
    void map_previewMediaResponseToCineMenuMediaResponseWhitPERSON() {
        List<Integer> genderIDs = new ArrayList<>();
        genderIDs.addAll(List.of(18, 80));
        List<String> originCountry = new ArrayList<>();
        originCountry.add("US");
        // Given
        var previewMediaResultResponse = new PreviewMediaResults.PreviewMediaResultResponse(
                false, null, 3084L, null, null, null,
                null, null, "/fuTEPMsBtV1zE98ujPONbKiYDc2.jpg", "person", genderIDs, 0.0, null,
                false, 0.0, 0, "Marlon Brando", "Marlon Brando", null, null
        );

        // When
        CineMenuMediaResponse cineMenuMediaResponse = PreviewMediaMapper.genericMediaMap(previewMediaResultResponse);

        // Then
        assertEquals(3084, cineMenuMediaResponse.id());
        assertEquals("Marlon Brando", cineMenuMediaResponse.title());
        assertEquals("/fuTEPMsBtV1zE98ujPONbKiYDc2.jpg", cineMenuMediaResponse.poster_path());
        assertEquals(MediaType.PERSON, cineMenuMediaResponse.media_type());
        assertEquals(null, cineMenuMediaResponse.release_date());
        assertEquals(0.0, cineMenuMediaResponse.vote_average());
    }

    @Test
    @DisplayName("Test movieMediaMap")
    void testMovieMediaMap() {
        // Given
        PreviewMediaResults.PreviewMediaResultResponse response = new PreviewMediaResults.PreviewMediaResultResponse(
                false, "backdrop_path", 1L, "Movie Title", "en", "Movie Title",
                "Overview", "/poster.jpg", null, "movie", List.of(28, 35), 0.0,
                "0000-00-00", false, 0.0, 0, null, null, null, null
        );

        // When
        CineMenuMediaResponse result = PreviewMediaMapper.movieMediaMap(response);

        // Then
        assertEquals(1, result.id());
        assertEquals("Movie Title", result.title());
        assertEquals("/poster.jpg", result.poster_path());
        assertEquals(MediaType.MOVIE, result.media_type());
        assertEquals("0000-00-00", result.release_date());
        assertEquals(0.0, result.vote_average());
    }

    @Test
    @DisplayName("Test tvMediaMap")
    void testTvMediaMap() {
        // Given
        PreviewMediaResults.PreviewMediaResultResponse response = new PreviewMediaResults.PreviewMediaResultResponse(
                false, null, 1L, null, "pt-br", null, "Overview",
                "/poster.jpg", null, "tv", List.of(35), 0.0, null,
                false, 0.0, 0, "TV Show", "TV Show", "0000-00-00", null
        );

        // When
        CineMenuMediaResponse result = PreviewMediaMapper.tvMediaMap(response);

        // Then
        assertEquals(1, result.id());
        assertEquals("TV Show", result.title());
        assertEquals("/poster.jpg", result.poster_path());
        assertEquals(MediaType.TV, result.media_type());
        assertEquals("0000-00-00", result.release_date());
        assertEquals(0.0, result.vote_average());
    }

    @Test
    @DisplayName("Test movieMediaMap method")
    void testMovieMediaMap02() {
        // Given
        PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse response =
                new PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse(
                        true, "backdrop_path_value", List.of(1, 2, 3), 12345L, "original_name_value",
                        "original_title_value", "name_value", "title_value", "original_language_value",
                        "overview_value", 7.8, "poster_path_value", "release_date_value",
                        "first_air_date_value", true, 6.9, 1000, "character_value",
                        "credit_id_value", 10, 1
                );

        // When
        CineMenuMediaResponse result = PreviewMediaMapper.movieMediaMap(response);

        // Then
        Assertions.assertEquals(response.id(), result.id());
        Assertions.assertEquals(response.title(), result.title());
        Assertions.assertEquals(response.posterPath(), result.poster_path());
        Assertions.assertEquals(MediaType.MOVIE, result.media_type());
        Assertions.assertEquals(response.releaseDate(), result.release_date());
        Assertions.assertEquals(response.voteAverage(), result.vote_average());
    }

    @Test
    @DisplayName("Test tvMediaMap method whit PreviewActorCreditsResultResponse param")
    void tvMediaMapTest() {
        // Given
        PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse response =
                new PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse(
                        true, "backdrop_path_value", List.of(1, 2, 3), 12345L, "original_name_value",
                        "original_title_value", "name_value", "title_value", "original_language_value",
                        "overview_value", 7.8, "poster_path_value", "release_date_value",
                        "first_air_date_value", true, 6.9, 1000, "character_value",
                        "credit_id_value", 10, 1
                );

        // When
        CineMenuMediaResponse result = PreviewMediaMapper.tvMediaMap(response);

        // Then
        Assertions.assertEquals(response.id(), result.id());
        Assertions.assertEquals(response.name(), result.title());
        Assertions.assertEquals(response.posterPath(), result.poster_path());
        Assertions.assertEquals(MediaType.TV, result.media_type());
        Assertions.assertEquals(response.firstAirDate(), result.release_date());
        Assertions.assertEquals(response.voteAverage(), result.vote_average());
    }

    @Test
    void testPersonMediaMap_WithValidResponse_ShouldReturnCineMenuMediaResponse() {
        // Given
        List<PreviewMediaResults.PreviewMediaResultResponse> knownFor = new ArrayList<>();

        PreviewPopularResults.PreviewPopularResultsResponse response = new PreviewPopularResults.PreviewPopularResultsResponse(
                false, 1, 1L, knownFor, "acting", "John Doe",
                0.0, "/profile.jpg"
        );

        // When
        CineMenuMediaResponse result = PreviewMediaMapper.personMediaMap(response);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John Doe", result.title());
        assertEquals("/profile.jpg", result.poster_path());
        assertEquals(MediaType.PERSON, result.media_type());
        assertNull(result.release_date());
        assertNull(result.vote_average());
    }
}
