package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TMDBInternalGenreMapperTest {

    @Test
    @DisplayName("Test map")
    void testMap() {
        // Given
        List<CineMenuGenres> genres = Arrays.asList(
                CineMenuGenres.ACTION,
                CineMenuGenres.ADVENTURE,
                CineMenuGenres.ANIMATION,
                CineMenuGenres.COMEDY,
                CineMenuGenres.CRIME,
                CineMenuGenres.DRAMA,
                CineMenuGenres.DOCUMENTARY,
                CineMenuGenres.FAMILY,
                CineMenuGenres.FANTASY,
                CineMenuGenres.HISTORY,
                CineMenuGenres.HORROR,
                CineMenuGenres.MUSIC,
                CineMenuGenres.MYSTERY,
                CineMenuGenres.ROMANCE,
                CineMenuGenres.SCIENCE_FICTION
        );

        List<Integer> expectedGenreIds = Arrays.asList(
                28, 18, 10759, 16, 35, 80, 18, 99, 10751, 14, 36, 10768, 27, 10402, 9648, 10749, 878, 10765
                );

        // When
        List<Integer> actualGenreIds = TMDBInternalGenreMapper.map(genres);

        // Then
        assertEquals(expectedGenreIds, actualGenreIds);
    }

}