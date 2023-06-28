package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewActorCreditsListResults;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResults;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewPopularResults;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.TMDBNotFoundException;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import br.com.cinemenu.cinemenuapi.rest.mapper.TMDBInternalGenreMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
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
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TMBD_MULTI_SEARCH_BASE_URL = "/search/multi?api_key=";
    private static final String TMBD_POPULAR_PERSON = "/person/popular?api_key=";
    private static final String TMDB_DISCOVERY_MOVIE_BASE_URL = "/discover/movie?api_key=";
    private static final String TMDB_DISCOVERY_TV_BASE_URL = "/discover/tv?api_key=";
    private static final String TMDB_ACTOR_MOVIE_LIST = "/person/%d/movie_credits?api_key=";
    private static final String TMDB_ACTOR_TV_LIST = "/person/%d/tv_credits?api_key=";
    private static final String TMDB_LANGUAGE_PT_BR = "&language=pt-BR";
    private static final String TMDB_BASE_EN_LANGUAGE = "&language=en-US";
    private static final String TMDB_SORT_BY_POP = "&sort_by=popularity.desc";
    private static final String TMDB_SORT_BY_VOTE = "&sort_by=vote_average.desc";
    private static final String TMDB_BASE_PAGE = "&page=";
    private static final String TMDB_ADULT_FALSE = "&include_adult=false";
    private static final String TMDB_USER_QUERY = "&query=";
    private static final String TMDB_WHIT_GENDER = "&with_genres=";
    private static final String TMDB_WATCH_REGION = "&region=US%2CBR";
    private static final String AND_URL = "%2C";
    private static final String OR_URL = "%7C";

    private static final Integer MAX_PAGES = 500;
    private static RestTemplate restTemplate = new RestTemplate();

    public PreviewMediaResults getSearchPreviewMediaResponse(String search, Integer page) {
        if (apiKey == null) throw new InvalidApiKeyException("invalid api key");
        verifyPage(page);

        String userQuery = StringUtils.stripAccents(search.trim().replaceAll(" ", "+").toLowerCase());


        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMBD_MULTI_SEARCH_BASE_URL + apiKey
                        + TMDB_LANGUAGE_PT_BR + TMDB_BASE_PAGE + page
                        + TMDB_ADULT_FALSE + TMDB_USER_QUERY + userQuery);

        var apiResponse = restTemplate.getForObject(uri, PreviewMediaResults.class);

        return apiResponse;
    }

    public PreviewMediaResponsePage getGenrePreviewMediaResponse(List<Integer> genreId, Integer page) {
        if (genreId.isEmpty()) throw new InvalidSearchException("Genre id most be provided");
        verifyPage(page);

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
                        + TMDB_ADULT_FALSE + TMDB_LANGUAGE_PT_BR
                        + TMDB_BASE_PAGE + page + TMDB_WATCH_REGION
                        + TMDB_SORT_BY_POP + TMDB_WHIT_GENDER + formattedMovieUrl
        );

        URI tvUri = URI.create(
                TMDB_BASE_URL
                        + TMDB_DISCOVERY_TV_BASE_URL + apiKey
                        + TMDB_ADULT_FALSE + TMDB_LANGUAGE_PT_BR
                        + TMDB_BASE_PAGE + page + TMDB_WATCH_REGION
                        + TMDB_SORT_BY_POP + TMDB_WHIT_GENDER + formattedTvUrl
        );

        var apiResponseMovie = restTemplate.getForObject(movieUri, PreviewMediaResults.class);
        var apiResponseTv = restTemplate.getForObject(tvUri, PreviewMediaResults.class);

        mediaList.addAll(apiResponseMovie.results().stream().map(PreviewMediaMapper::movieMediaMap).toList());
        mediaList.addAll(apiResponseTv.results().stream().map(PreviewMediaMapper::tvMediaMap).toList());
        Collections.shuffle(mediaList);

        totalPages = apiResponseMovie.total_pages() + apiResponseTv.total_pages();

        if (totalPages > MAX_PAGES) totalPages = MAX_PAGES;

        return new PreviewMediaResponsePage(page, mediaList, totalPages);
    }

    public PreviewPopularResults getPeopleListResults(Integer page) {
        verifyPage(page);

        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMBD_POPULAR_PERSON
                        + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_BASE_PAGE + page
        );

        return restTemplate.getForObject(uri, PreviewPopularResults.class);
    }

    public PreviewActorCreditsListResults getMovieListByActorId(Long id) {
        verifyId(id);

        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_ACTOR_MOVIE_LIST.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
        );

        try {
            return restTemplate.getForObject(uri, PreviewActorCreditsListResults.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException("id not found");
        }
    }

    public PreviewActorCreditsListResults getSeriesListByActorId(Long id) {
        verifyId(id);

        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_ACTOR_TV_LIST.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
        );

        try {
            return restTemplate.getForObject(uri, PreviewActorCreditsListResults.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException("id not found");
        }
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private static void verifyPage(Integer page) {
        if (page < 1 || page > MAX_PAGES)
            throw new InvalidSearchException("search page cannot be less then 1 or more than %d".formatted(MAX_PAGES));
    }

    private static void verifyId(Long id) {
        if (id == null) throw new InvalidSearchException("invalid search, actor id can not be null.");
    }
}
