package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponse;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Repository
public class PreviewMediaRepository {

    @Value("${api.key.from.tmdb}")
    public String apiKey;
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private final String TMBD_MULTI_SEARCH_BASE_URL = "/search/multi?api_key=";
    private final String TMDB_BASE_PT_BR_LANGUAGE = "&language=pt-BR";
    //private final String TMDB_BASE_EN_LANGUAGE = "&language=en-US";
    private final String TMDB_BASE_PAGE = "&page=";
    private final String TMDB_ADULT_FALSE_USER_QUERY = "&include_adult=false&query=";

    public PreviewMediaResponse getPreviewMediaResponse(String search, Integer page) {
        if (apiKey == null) throw new InvalidApiKeyException("invalid api key");
        if (search.isBlank() || page == null) throw new InvalidSearchException("invalid search or page");

        String userQuery = search.trim().replaceAll(" ", "+").toLowerCase();

        RestTemplate restTemplate = new RestTemplate();

        URI uri = URI.create(
                TMDB_BASE_URL + TMBD_MULTI_SEARCH_BASE_URL + apiKey
                        + TMDB_BASE_PT_BR_LANGUAGE + TMDB_BASE_PAGE + page
                        + TMDB_ADULT_FALSE_USER_QUERY + userQuery);

        var apiResponse = restTemplate.getForObject(uri, PreviewMediaResponse.class);
        return apiResponse;
    }
}
