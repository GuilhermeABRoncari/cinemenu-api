package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.*;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.TMDBNotFoundException;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
class PreviewMediaRepositoryTest {

    @InjectMocks
    @Autowired
    private PreviewMediaRepository repository;
    @Value("${api.key.from.tmdb}")
    @Autowired
    private String apiKey;
    @Spy
    RestTemplate restTemplate = new RestTemplate();
    private String search;
    private int page;
    private List<Integer> cineMenuGenres = new ArrayList<>();
    private PreviewMediaResults.PreviewMediaResultResponse genericResponse;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        search = "carros+3";
        page = 1;
        genericResponse = new PreviewMediaResults.PreviewMediaResultResponse(
                false, "backdrop_path", 1L, "Title", "pt-BR", "Title",
                "Overview", "poster_path", "profile_path", "movie", List.of(1,2,3),
                0.0, "0000-00-00", false, 0.0, 0, "name", "name",
                "0000-00-00", List.of("BR"));
    }

    @Test
    @DisplayName("When API key is null, it should throw InvalidApiKeyException")
    void getPreviewMediaResponseNullApiKeyShouldThrowInvalidApiKeyException() {
        Assertions.assertThrows(InvalidApiKeyException.class, () -> {
            repository.setApiKey(null);
            repository.getSearchPreviewMediaResponse(search, page);
        });
    }

    @Test
    @DisplayName("When page is less than 1, it should throw InvalidSearchException")
    void testGetPreviewMediaResponseInvalidPage() {
        int invalidPage = 0;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getSearchPreviewMediaResponse(search, invalidPage);
        });
    }

    @Test
    @DisplayName("When page is greater than 1000, it should throw InvalidSearchException")
    void getPreviewMediaResponsePageGreaterThanMaxShouldThrowInvalidSearchException() {
        int invalidPage = 501;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getSearchPreviewMediaResponse(search, invalidPage);
        });
    }

    @Test
    @DisplayName("When page is less than 1, it should throw InvalidSearchException")
    void getGenrePreviewMediaResponseTestScene01() {
        int invalidPage = 0;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getGenrePreviewMediaResponse(List.of(67), invalidPage);
        });
    }

    @Test
    @DisplayName("When page is greater than 500, it should throw InvalidSearchException")
    void getGenrePreviewMediaResponseTestScene02() {
        int invalidPage = 501;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getGenrePreviewMediaResponse(List.of(67), invalidPage);
        });
    }

    @Test
    @DisplayName("When genreList is null, it should throw InvalidSearchException")
    void getGenrePreviewMediaResponseTestScene03() {
        cineMenuGenres.clear();

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getGenrePreviewMediaResponse(cineMenuGenres, page);
        });
    }

    @Test
    @DisplayName("When getting popular people list, it should return the expected result")
    void getPeopleListResultsValidPageShouldReturnExpectedResults() {
        // Given
        Integer page = 1;
        URI expectedUri = URI.create(
                "https://api.themoviedb.org/3/person/popular?api_key=" + apiKey
                        + "&language=pt-BR&page=" + page
        );

        PreviewPopularResults expectedResult = restTemplate.getForObject(expectedUri, PreviewPopularResults.class);

        // When
        PreviewPopularResults result = repository.getPeopleListResults(page);

        // Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getMovieListByActorId method")
    void testGetMovieListByActorId() {
        // Given
        Long renatoAragaoId = 936457L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/person/%d/movie_credits?api_key=".formatted(renatoAragaoId) + apiKey
                        + "&language=pt-BR");

        var expectedResult = restTemplate.getForObject(expectedUri, PreviewActorCreditsListResults.class);

        // When
        var result = repository.getMovieListByActorId(renatoAragaoId);

        // Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getSeriesListByActorId method")
    void testGetSeriesListByActorId() {
        // Given
        Long renatoAragaoId = 936457L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/person/%d/tv_credits?api_key=".formatted(renatoAragaoId) + apiKey
                        + "&language=pt-BR");

        var expectedResult = restTemplate.getForObject(expectedUri, PreviewActorCreditsListResults.class);

        // When
        var result = repository.getSeriesListByActorId(renatoAragaoId);

        // Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getMovieListByActorId method and catch TMDBNotFoundException")
    void testGetMovieListByActorId02() {
        // Given
        Long invalidId = 125L;

        // Then
        Assertions.assertThrows(TMDBNotFoundException.class, () -> {
            repository.getMovieListByActorId(invalidId);
        });
    }

    @Test
    @DisplayName("Test getSeriesListByActorId method and catch TMDBNotFoundException")
    void testGetSeriesListByActorId02() {
        // Given
        Long invalidId = 125L;

        // Then
        Assertions.assertThrows(TMDBNotFoundException.class, () -> {
            repository.getSeriesListByActorId(invalidId);
        });
    }

    @Test
    @DisplayName("Test method verifyId")
    void verifyIdTest() {
        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getMovieListByActorId(null);
        });
    }

    @Test
    @DisplayName("Test method getSimilarTVShowList whit invalid id")
    void getSimilarTVShowListByIdTest02() {
        // Given
        Long invalidId = 9898989L;
        Integer page = 1;

        // Then
        Assertions.assertThrows(TMDBNotFoundException.class, () -> {
            repository.getSimilarTVShowListById(invalidId, page);
        });
    }

    @Test
    @DisplayName("Test method getSimilarMovieList whit correct variables")
    void getSimilarMovieListByIdTest01() {
        // Given
        Long cars3Id = 260514L;
        Integer page = 1;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d/similar?api_key=".formatted(cars3Id) + apiKey
                        + "&language=pt-BR&page=" + page
        );

        var expectedResult = restTemplate.getForObject(expectedUri, PreviewMediaResults.class);

        // When
        var result = repository.getSimilarMovieListById(cars3Id, page);

        // Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test method getSimilarMovieList whit invalid id")
    void getSimilarMovieListByIdTest02() {
        // Given
        Long invalidId = 9898989L;
        Integer page = 1;

        // Then
        Assertions.assertThrows(TMDBNotFoundException.class, () -> {
            repository.getSimilarMovieListById(invalidId, page);
        });
    }

    @Test
    @DisplayName("Test getTvShowDetailsById() method whit valid id")
    void getTvShowDetailsByIdScene01() {
        // Given
        Long breakingBadId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d?api_key=".formatted(breakingBadId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewTvShowDetailsResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewTvShowDetailsResultDto.class);

        // When
        PreviewTvShowDetailsResultDto result = repository.getTvShowDetailsById(breakingBadId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getTvShowDetailsById() method whit invalid id")
    void getTvShowDetailsByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getTvShowDetailsById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewTvShowDetailsResultDto.class);
        });
    }

    @Test
    @DisplayName("Test getMovieDetailsById() method whit valid id")
    void getMovieDetailsByIdScene01() {
        // Given
        Long findingNemoId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d?api_key=".formatted(findingNemoId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewMovieDetailsResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewMovieDetailsResultDto.class);

        // When
        PreviewMovieDetailsResultDto result = repository.getMovieDetailsById(findingNemoId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getMovieDetailsById() method whit invalid id")
    void getMovieDetailsByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getMovieDetailsById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewMovieDetailsResultDto.class);
        });
    }

    @Test
    @DisplayName("Test getTvShowCreditsById() method whit valid id")
    void getTvShowCreditsByIdScene01() {
        // Given
        Long findingNemoId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d/credits?api_key=".formatted(findingNemoId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewTvShowCreditsResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewTvShowCreditsResultDto.class);

        // When
        PreviewTvShowCreditsResultDto result = repository.getTvShowCreditsById(findingNemoId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getTvShowCreditsById() method whit invalid id")
    void getTvShowCreditsByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d/credits?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getTvShowCreditsById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewTvShowCreditsResultDto.class);
        });
    }

    @Test
    @DisplayName("Test getMovieCreditsById() method whit valid id")
    void getMovieCreditsByIdScene01() {
        // Given
        Long findingNemoId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d/credits?api_key=".formatted(findingNemoId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewMovieCreditsResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewMovieCreditsResultDto.class);

        // When
        PreviewMovieCreditsResultDto result = repository.getMovieCreditsById(findingNemoId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getMovieCreditsById() method whit invalid id")
    void getMovieCreditsByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d/credits?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getMovieCreditsById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewMovieCreditsResultDto.class);
        });
    }

    @Test
    @DisplayName("Test getMovieWatchProvidersById() method whit valid id")
    void getMovieWatchProvidersByIdScene01() {
        // Given
        Long findingNemoId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d/watch/providers?api_key=".formatted(findingNemoId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewMovieWatchProvidersResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewMovieWatchProvidersResultDto.class);

        // When
        PreviewMovieWatchProvidersResultDto result = repository.getMovieWatchProvidersById(findingNemoId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getMovieWatchProvidersById() method whit invalid id")
    void getMovieWatchProvidersByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d/watch/providers?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getMovieWatchProvidersById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewMovieWatchProvidersResultDto.class);
        });
    }

    @Test
    @DisplayName("Test getTvShowWatchProvidersById() method whit valid id")
    void getTvShowWatchProvidersByIdScene01() {
        // Given
        Long findingNemoId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d/watch/providers?api_key=".formatted(findingNemoId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewTvShowWatchProvidersResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewTvShowWatchProvidersResultDto.class);

        // When
        PreviewTvShowWatchProvidersResultDto result = repository.getTvShowWatchProvidersById(findingNemoId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getTvShowWatchProvidersById() method whit invalid id")
    void getTvShowWatchProvidersByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d/watch/providers?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getTvShowWatchProvidersById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewTvShowWatchProvidersResultDto.class);
        });
    }

    @Test
    @DisplayName("Test getTvShowVideosById() method whit valid id")
    void getTvShowVideosByIdScene01() {
        // Given
        Long findingNemoId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d/videos?api_key=".formatted(findingNemoId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewTvShowVideoResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewTvShowVideoResultDto.class);

        // When
        PreviewTvShowVideoResultDto result = repository.getTvShowVideosById(findingNemoId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getTvShowVideosById() method whit invalid id")
    void getTvShowVideosByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/tv/%d/videos?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getTvShowVideosById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewTvShowVideoResultDto.class);
        });
    }

    @Test
    @DisplayName("Test getMovieVideosById() method whit valid id")
    void getMovieVideosByIdScene01() {
        // Given
        Long findingNemoId = 1396L;
        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d/videos?api_key=".formatted(findingNemoId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        PreviewMovieVideoResultDto expectedResult = restTemplate.getForObject(expectedUri, PreviewMovieVideoResultDto.class);

        // When
        PreviewMovieVideoResultDto result = repository.getMovieVideosById(findingNemoId);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Test getMovieVideosById() method whit invalid id")
    void getMovieVideosByIdScene02() {
        // Given
        Long invalidId = 9999999999L;

        URI expectedUri = URI.create(
                "http://api.themoviedb.org/3/movie/%d/videos?api_key=".formatted(invalidId) + apiKey + "&language=pt-BR&include_adult=false"
        );

        // When // Then
        assertThrows(TMDBNotFoundException.class, () -> {
            repository.getMovieVideosById(invalidId);
        });

        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(expectedUri, PreviewMovieVideoResultDto.class);
        });
    }
}