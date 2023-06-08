package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponse;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Repository
public class PreviewMediaRepository {

    @Value("${api.key.from.tmdb}")
    private String apiKey;
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private final String TMBD_MULTI_SEARCH_BASE_URL = "/search/multi?api_key=";
    private final String TMDB_BASE_PT_BR_LANGUAGE = "&language=pt-BR";
    //private final String TMDB_BASE_EN_LANGUAGE = "&language=en-US";
    private final String TMDB_BASE_PAGE = "&page=";
    private final String TMDB_ADULT_FALSE_USER_QUERY = "&include_adult=false&query=";

    public PreviewMediaResponse getPreviewMediaResponse(String search, int page) {
        if (apiKey == null) throw new InvalidApiKeyException("invalid api key");
        if (page < 1 || page > 1000) throw new InvalidSearchException("search page cannot be less then 1 or more than 1000");

        String userQuery = StringUtils.stripAccents(search.trim().replaceAll(" ", "+").toLowerCase());

        RestTemplate restTemplate = new RestTemplate();

        URI uri = URI.create(
                TMDB_BASE_URL + TMBD_MULTI_SEARCH_BASE_URL + apiKey
                        + TMDB_BASE_PT_BR_LANGUAGE + TMDB_BASE_PAGE + page
                        + TMDB_ADULT_FALSE_USER_QUERY + userQuery);

        var apiResponse = restTemplate.getForObject(uri, PreviewMediaResponse.class);

        return apiResponse;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
