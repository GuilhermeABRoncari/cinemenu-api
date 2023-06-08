package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponse;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootTest
class PreviewMediaRepositoryTest {

    @InjectMocks
    @Autowired
    private PreviewMediaRepository repository;
    @Value("${api.key.from.tmdb}")
    @Autowired
    private String apiKey;
    private String search;
    private int page;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        search = "carros+3";
        page = 1;
    }

    @Test
    @DisplayName("When valid search is performed, it should return the preview media response")
    void getPreviewMediaResponse_validSearch_shouldReturnResponse() {
        RestTemplate restTemplate = new RestTemplate();

        URI expectedUri = URI.create("https://api.themoviedb.org/3//search/multi?api_key=" + apiKey + "&language=pt-BR&page=" + page + "&include_adult=false&query=" + search);
        PreviewMediaResponse apiResponse = restTemplate.getForObject(expectedUri, PreviewMediaResponse.class);

        PreviewMediaResponse response = repository.getPreviewMediaResponse(search, page);

        // Assertions
        Assertions.assertEquals(apiResponse, response);
    }

    @Test
    @DisplayName("When API key is null, it should throw InvalidApiKeyException")
    void getPreviewMediaResponse_nullApiKey_shouldThrowInvalidApiKeyException() {
        Assertions.assertThrows(InvalidApiKeyException.class, () -> {
            repository.setApiKey(null);
            repository.getPreviewMediaResponse(search, page);
        });
    }

    @Test
    @DisplayName("When page is less than 1, it should throw InvalidSearchException")
    void getPreviewMediaResponse_invalidPage_shouldThrowInvalidSearchException() {
        int invalidPage = 0;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getPreviewMediaResponse(search, invalidPage);
        });
    }

    @Test
    @DisplayName("When page is greater than 1000, it should throw InvalidSearchException")
    void getPreviewMediaResponse_pageGreaterThanMax_shouldThrowInvalidSearchException() {
        int invalidPage = 1001;

        Assertions.assertThrows(InvalidSearchException.class, () -> {
            repository.getPreviewMediaResponse(search, invalidPage);
        });
    }

}
