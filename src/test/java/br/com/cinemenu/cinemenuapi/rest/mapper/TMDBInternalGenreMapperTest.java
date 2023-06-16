package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TMDBInternalGenreMapperTest {

    @Test
    @DisplayName("When mapping movie genres to TMDB genre IDs, it should return the expected IDs")
    void mapToMovieIds_validGenres_shouldReturnExpectedIds() {
        // Given
        List<CineMenuGenres> genres = Arrays.asList(CineMenuGenres.values());
        List<Integer> expectedIds = Arrays.asList(
                28, 12, 16, 35, 80, 99, 18, 10751,
                14, 36, 27, 10402, 9648, 10749, 878
        );

        // When
        List<Integer> result = TMDBInternalGenreMapper.mapToMovieIds(genres);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedIds.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedIds));
    }

    @Test
    @DisplayName("When mapping TV show genres to TMDB genre IDs, it should return the expected IDs")
    void mapToTvShowIds_validGenres_shouldReturnExpectedIds() {
        // Given
        List<CineMenuGenres> genres = Arrays.asList(CineMenuGenres.values());
        List<Integer> expectedIds = Arrays.asList(
                10759, 10759, 16, 35, 80, 99, 18, 10751, 14,
                10768, 27, 10402, 9648, 10749, 10765
        );

        // When
        List<Integer> result = TMDBInternalGenreMapper.mapToTvShowIds(genres);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedIds.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedIds));
    }

}
