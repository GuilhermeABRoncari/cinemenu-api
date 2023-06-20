package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResults;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        search = "carros+3";
        page = 1;
    }

    @Test
    @DisplayName("When valid search is performed, it should return the preview media response")
    void getPreviewMediaResponse_validSearch_shouldReturnResponse() {

        URI expectedUri = URI.create("https://api.themoviedb.org/3//search/multi?api_key=" + apiKey + "&language=pt-BR&page=" + page + "&include_adult=false&query=" + search);
        PreviewMediaResults apiResponse = restTemplate.getForObject(expectedUri, PreviewMediaResults.class);

        PreviewMediaResults response = repository.getSearchPreviewMediaResponse(search, page);

        // Assertions
        Assertions.assertEquals(apiResponse, response);
    }

    @Test
    @DisplayName("When API key is null, it should throw InvalidApiKeyException")
    void getPreviewMediaResponse_nullApiKey_shouldThrowInvalidApiKeyException() {
        Assertions.assertThrows(InvalidApiKeyException.class, () -> {
            repository.setApiKey(null);
            repository.getSearchPreviewMediaResponse(search, page);
        });
    }

    @Test
    @DisplayName("When page is less than 1, it should throw InvalidSearchException")
    void getPreviewMediaResponse_invalidPage_shouldThrowInvalidSearchException() {
        int invalidPage = 0;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getSearchPreviewMediaResponse(search, invalidPage);
        });
    }

    @Test
    @DisplayName("When page is greater than 1000, it should throw InvalidSearchException")
    void getPreviewMediaResponse_pageGreaterThanMax_shouldThrowInvalidSearchException() {
        int invalidPage = 501;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getSearchPreviewMediaResponse(search, invalidPage);
        });
    }

    @Test
    @DisplayName("When valid request is performed, it should return the PreviewMediaResponsePage")
    void getGenrePreviewMediaResponseTest() {
        cineMenuGenres = List.of(67);
        var TMDBInternalGenre = 28;
        List<CineMenuMediaResponse> mediaList = new ArrayList<>();

        URI expectedMovieUri = URI.create(
                "https://api.themoviedb.org/3//discover/movie?api_key=" + apiKey
                        + "&include_adult=false&language=pt-BR&page=" + page
                        + "&region=US%2CBR&sort_by=popularity.desc&with_genres=" + TMDBInternalGenre);
        URI expectedTvUri = URI.create(
                "https://api.themoviedb.org/3//discover/tv?api_key=" + apiKey
                        + "&include_adult=false&language=pt-BR&page=" + page
                        + "&region=US%2CBR&sort_by=popularity.desc&with_genres=" + TMDBInternalGenre);

        var movieResponse = restTemplate.getForObject(expectedMovieUri, PreviewMediaResults.class);
        var tvResponse = restTemplate.getForObject(expectedTvUri, PreviewMediaResults.class);

        mediaList.addAll(movieResponse.results().stream().map(PreviewMediaMapper::movieMediaMap).toList());
        mediaList.addAll(tvResponse.results().stream().map(PreviewMediaMapper::tvMediaMap).toList());
        Collections.shuffle(mediaList);

        var result = new PreviewMediaResponsePage(page, mediaList, 500);
        var apiResponse = repository.getGenrePreviewMediaResponse(cineMenuGenres, page);

        Assertions.assertNotEquals(apiResponse, result);
    }

    @Test
    @DisplayName("When page is less than 1, it should throw InvalidSearchException")
    void getGenrePreviewMediaResponseTest_scene01() {
        int invalidPage = 0;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getGenrePreviewMediaResponse(List.of(67), invalidPage);
        });
    }

    @Test
    @DisplayName("When page is greater than 500, it should throw InvalidSearchException")
    void getGenrePreviewMediaResponseTest_scene02() {
        int invalidPage = 501;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getGenrePreviewMediaResponse(List.of(67), invalidPage);
        });
    }

    @Test
    @DisplayName("When genreList is null, it should throw InvalidSearchException")
    void getGenrePreviewMediaResponseTest_scene03() {
        cineMenuGenres.clear();

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getGenrePreviewMediaResponse(cineMenuGenres, page);
        });
    }
}