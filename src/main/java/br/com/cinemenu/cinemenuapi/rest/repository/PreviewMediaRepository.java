package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResults;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import br.com.cinemenu.cinemenuapi.rest.mapper.TMDBInternalGenreMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PreviewMediaRepository {

    @Value("${api.key.from.tmdb}")
    private String apiKey;
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private final String TMBD_MULTI_SEARCH_BASE_URL = "/search/multi?api_key=";
    private final String TMDB_DISCOVERY_MOVIE_BASE_URL = "/discover/movie?api_key=";
    private final String TMDB_DISCOVERY_TV_BASE_URL = "/discover/tv?api_key=";
    private final String TMDB_BASE_PT_BR_LANGUAGE = "&language=pt-BR";
    //private final String TMDB_BASE_EN_LANGUAGE = "&language=en-US";
    private final String TMDB_SORT_BY_POP = "&sort_by=popularity.desc";
    private final String TMDB_SORT_BY_VOTE = "&sort_by=vote_average.desc";
    private final String TMDB_BASE_PAGE = "&page=";
    private final String TMDB_ADULT_FALSE = "&include_adult=false";
    private final String TMDB_USER_QUERY = "&query=";
    private final String TMDB_WHIT_GENDER = "&with_genres=";
    private final String TMDB_WATCH_REGION = "&region=US%2CBR";
    private final String AND_URL = "%2C";
    private final String OR_URL = "%7C";

    private final Integer MAX_PAGES = 500;

    public PreviewMediaResults getSearchPreviewMediaResponse(String search, Integer page) {
        if (apiKey == null) throw new InvalidApiKeyException("invalid api key");
        if (page < 1 || page > MAX_PAGES) throw new InvalidSearchException("search page cannot be less then 1 or more than 1000");

        String userQuery = StringUtils.stripAccents(search.trim().replaceAll(" ", "+").toLowerCase());

        RestTemplate restTemplate = new RestTemplate();

        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMBD_MULTI_SEARCH_BASE_URL + apiKey
                        + TMDB_BASE_PT_BR_LANGUAGE + TMDB_BASE_PAGE + page
                        + TMDB_ADULT_FALSE + TMDB_USER_QUERY + userQuery);

        var apiResponse = restTemplate.getForObject(uri, PreviewMediaResults.class);

        return apiResponse;
    }

    public PreviewMediaResponsePage getGenrePreviewMediaResponse(List<Integer> genreId, Integer page) {
        if (genreId.isEmpty()) throw new InvalidSearchException("Genre id most be provided");
        if (page < 1 || page > MAX_PAGES) throw new InvalidSearchException("search page cannot be less then 1 or more than 500");

        RestTemplate restTemplate = new RestTemplate();
        List<CineMenuMediaResponse> mediaList = new ArrayList<>();
        Integer totalPages;

        List<CineMenuGenres> genreList = genreId.stream().map(id -> CineMenuGenres.fromId(id)).toList();

        List<Integer> MovieGenreIds = TMDBInternalGenreMapper.mapToMovieIds(genreList);
        String formattedMovieUrl = MovieGenreIds.stream().map(Object::toString).collect(Collectors.joining(AND_URL));

        List<Integer> TvGenreIds = TMDBInternalGenreMapper.mapToTvShowIds(genreList);
        String formattedTvUrl = TvGenreIds.stream().map(Object::toString).collect(Collectors.joining(AND_URL));

        URI movieUri = URI.create(
                TMDB_BASE_URL
                        + TMDB_DISCOVERY_MOVIE_BASE_URL + apiKey
                        + TMDB_ADULT_FALSE + TMDB_BASE_PT_BR_LANGUAGE
                        + TMDB_BASE_PAGE + page + TMDB_WATCH_REGION
                        + TMDB_SORT_BY_POP + TMDB_WHIT_GENDER + formattedMovieUrl
        );

        URI tvUri = URI.create(
                TMDB_BASE_URL
                        + TMDB_DISCOVERY_TV_BASE_URL + apiKey
                        + TMDB_ADULT_FALSE + TMDB_BASE_PT_BR_LANGUAGE
                        + TMDB_BASE_PAGE + page + TMDB_WATCH_REGION
                        + TMDB_SORT_BY_POP + TMDB_WHIT_GENDER + formattedTvUrl
        );

        var apiResponseMovie = restTemplate.getForObject(movieUri, PreviewMediaResults.class);
        var apiResponseTv = restTemplate.getForObject(tvUri, PreviewMediaResults.class);

        mediaList.addAll(apiResponseMovie.results().stream().map(PreviewMediaMapper::movieMediaMap).toList());
        mediaList.addAll(apiResponseTv.results().stream().map(PreviewMediaMapper::tvMediaMap).toList());
        Collections.shuffle(mediaList);

        totalPages = apiResponseMovie.total_pages() + apiResponseTv.total_pages();

        if (totalPages > 500) totalPages = 500;

        return new PreviewMediaResponsePage(page, mediaList, totalPages);
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
