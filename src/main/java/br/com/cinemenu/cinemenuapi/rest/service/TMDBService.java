package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TMDBService {

    @Value("${api.key.from.tmdb}")
    public String apiKey = "d2c81658e72d7ce0a1b6555c597e8c4c";
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private final String TMBD_MULTI_SEARCH_BASE_URL = "/search/multi?api_key=";
    private final String TMDB_BASE_IMG_URL = "https://image.tmdb.org/t/p/w500";
    private final String TMDB_BASE_PT_BR_LANGUAGE = "&language=pt-BR";
    //private final String TMDB_BASE_EN_LANGUAGE = "&language=en-US";
    private final String TMDB_BASE_PAGE = "&page=";
    private final String TMDB_ADULT_FALSE_USER_QUERY = "&include_adult=false&query=";
    public HttpClient httpClient;

    public String getResponse(String search, Integer page) {
        if(apiKey == null) throw new InvalidApiKeyException("invalid api key");
        if(search.isBlank() || page == null) throw new InvalidSearchException("invalid search or page");

        String userQuery = search.trim().replaceAll(" ", "+").toLowerCase();

        URI uri = URI.create(
                TMDB_BASE_URL + TMBD_MULTI_SEARCH_BASE_URL + apiKey
                        + TMDB_BASE_PT_BR_LANGUAGE + TMDB_BASE_PAGE + page
                        + TMDB_ADULT_FALSE_USER_QUERY + userQuery);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        HttpResponse response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonObject = JsonParser.parseString((String) response.body()).getAsJsonObject();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(jsonObject);

            return prettyJson;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
