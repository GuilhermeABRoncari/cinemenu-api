package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResults;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreviewMediaMapperTest {

    @Test
    @DisplayName("Test mapping from PreviewMediaResponse to CineMenuMediaResponse whit MediaType.MOVIE")
    void map_previewMediaResponseToCineMenuMediaResponseWhitMOVE() {
        List<Integer> genderIDs = new ArrayList<>();
        genderIDs.addAll(List.of(16, 12, 35, 10751));
        // Given
        var previewMediaResultResponse = new PreviewMediaResults.PreviewMediaResultResponse(
                false, "/4mlS1MitcOqdPLhxEFyk9Qwf5rr.jpg", 260514, "Carros 3", "en", "Cars 3",
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
                false, "/84XPpjGvxNyExjSuLQe0SzioErt.jpg", 1396, null, "en", null,
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
                false, null, 3084, null, null, null,
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
                false, "backdrop_path", 1, "Movie Title", "en", "Movie Title",
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
                false, null, 1, null, "pt-br", null, "Overview",
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
}
