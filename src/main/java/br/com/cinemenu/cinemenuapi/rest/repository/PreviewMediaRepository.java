package br.com.cinemenu.cinemenuapi.rest.repository;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.*;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidApiKeyException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.TMDBNotFoundException;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import br.com.cinemenu.cinemenuapi.rest.mapper.TMDBInternalGenreMapper;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PreviewMediaRepository {

    @Value("${api.key.from.tmdb}")
    private String apiKey;
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TMDB_MOVIE_DETAIL = "/movie/%d?api_key=";
    private static final String TMDB_MOVIE_CREDITS = "/movie/%d/credits?api_key=";
    private static final String TMDB_MOVIE_WATCH_PROVIDERS = "/movie/%d/watch/providers?api_key=";
    private static final String TMDB_MOVIE_VIDEO = "/movie/%d/videos?api_key=";
    private static final String TMDB_TV_DETAIL = "/tv/%d?api_key=";
    private static final String TMDB_TV_CREDITS = "/tv/%d/credits?api_key=";
    private static final String TMDB_TV_WATCH_PROVIDERS = "/tv/%d/watch/providers?api_key=";
    private static final String TMDB_TV_VIDEO = "/tv/%d/videos?api_key=";
    private static final String TMBD_MULTI_SEARCH_BASE_URL = "/search/multi?api_key=";
    private static final String TMBD_POPULAR_PERSON = "/person/popular?api_key=";
    private static final String TMDB_DISCOVERY_MOVIE_BASE_URL = "/discover/movie?api_key=";
    private static final String TMDB_DISCOVERY_TV_BASE_URL = "/discover/tv?api_key=";
    private static final String TMDB_ACTOR_MOVIE_LIST = "/person/%d/movie_credits?api_key=";
    private static final String TMDB_ACTOR_TV_LIST = "/person/%d/tv_credits?api_key=";
    private static final String TMDB_SIMILAR_TV_LIST = "/tv/%d/similar?api_key=";
    private static final String TMDB_SIMILAR_MOVIE_LIST = "/movie/%d/similar?api_key=";
    private static final String TMDB_LANGUAGE_PT_BR = "&language=pt-BR";
    private static final String TMDB_SORT_BY_POP = "&sort_by=popularity.desc";
    private static final String TMDB_BASE_PAGE = "&page=";
    private static final String TMDB_ADULT_FALSE = "&include_adult=false";
    private static final String TMDB_USER_QUERY = "&query=";
    private static final String TMDB_WHIT_GENDER = "&with_genres=";
    private static final String TMDB_WATCH_REGION = "&region=US%2CBR";
    private static final String AND_URL = "%2C";
    private static final String ID_NOT_FOUND = "id not found: %d";
    private static final Integer MAX_PAGES = 500;
    private static final String INVALID_API_KEY = "invalid api key";
    private static final String PROVIDE_GENRE = "Genre id most be provided";
    private static final String INVALID_SEARCH_MAX_PAGES = "search page cannot be less then 1 or more than %d";
    private static final String INVALID_SEARCH_ACTOR_ID = "invalid search, actor id can not be null.";
    private static final RestTemplate restTemplate = new RestTemplate();

    public PreviewMediaResults getSearchPreviewMediaResponse(String search, Integer page) {
        if (apiKey == null) throw new InvalidApiKeyException(INVALID_API_KEY);
        verifyPage(page);

        String userQuery = StringUtils.stripAccents(search.trim().replace(" ", "+").toLowerCase());

        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMBD_MULTI_SEARCH_BASE_URL + apiKey
                        + TMDB_LANGUAGE_PT_BR + TMDB_BASE_PAGE + page
                        + TMDB_ADULT_FALSE + TMDB_USER_QUERY + userQuery);

        return restTemplate.getForObject(uri, PreviewMediaResults.class);
    }

    public PreviewMediaResponsePage getGenrePreviewMediaResponse(List<Integer> genreId, Integer page) {
        if (genreId.isEmpty()) throw new InvalidSearchException(PROVIDE_GENRE);
        verifyPage(page);

        List<CineMenuMediaResponse> mediaList = new ArrayList<>();
        int totalPages;

        List<CineMenuGenres> genreList = genreId.stream().map(CineMenuGenres::fromId).toList();

        List<Integer> movieGenreIds = TMDBInternalGenreMapper.mapToTMDBMovieIds(genreList);
        String formattedMovieUrl = movieGenreIds.stream().map(Object::toString).collect(Collectors.joining(AND_URL));

        List<Integer> tvGenreIds = TMDBInternalGenreMapper.mapToTMDBTvShowIds(genreList);
        String formattedTvUrl = tvGenreIds.stream().map(Object::toString).collect(Collectors.joining(AND_URL));

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

        if (Objects.requireNonNull(apiResponseMovie).results() != null)
            mediaList.addAll(apiResponseMovie.results().stream().map(PreviewMediaMapper::movieMediaMap).toList());
        if (Objects.requireNonNull(apiResponseTv).results() != null)
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
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
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
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewMediaResults getSimilarTVShowListById(Long id, Integer page) {
        verifyId(id);
        verifyPage(page);

        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_SIMILAR_TV_LIST.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_BASE_PAGE + page
        );

        try {
            return restTemplate.getForObject(uri, PreviewMediaResults.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewMediaResults getSimilarMovieListById(Long id, Integer page) {
        verifyId(id);
        verifyPage(page);

        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_SIMILAR_MOVIE_LIST.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_BASE_PAGE + page
        );

        try {
            return restTemplate.getForObject(uri, PreviewMediaResults.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewTvShowDetailsResultDto getTvShowDetailsById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_TV_DETAIL.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewTvShowDetailsResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewMovieDetailsResultDto getMovieDetailsById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_MOVIE_DETAIL.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewMovieDetailsResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewTvShowCreditsResultDto getTvShowCreditsById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_TV_CREDITS.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewTvShowCreditsResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewMovieCreditsResultDto getMovieCreditsById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_MOVIE_CREDITS.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewMovieCreditsResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewTvShowWatchProvidersResultDto getTvShowWatchProvidersById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_TV_WATCH_PROVIDERS.formatted(id) + apiKey
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewTvShowWatchProvidersResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewMovieWatchProvidersResultDto getMovieWatchProvidersById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_MOVIE_WATCH_PROVIDERS.formatted(id) + apiKey
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewMovieWatchProvidersResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    @Generated
    public PreviewTvShowVideoResultDto getTvShowVideosById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_TV_VIDEO.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewTvShowVideoResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public PreviewMovieVideoResultDto getMovieVideosById(Long id) {
        URI uri = URI.create(
                TMDB_BASE_URL
                        + TMDB_MOVIE_VIDEO.formatted(id) + apiKey
                        + TMDB_LANGUAGE_PT_BR
                        + TMDB_ADULT_FALSE
        );

        try {
            return restTemplate.getForObject(uri, PreviewMovieVideoResultDto.class);
        } catch (HttpClientErrorException ex) {
            throw new TMDBNotFoundException(ID_NOT_FOUND.formatted(id));
        }
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private void verifyPage(Integer page) {
        if (page < 1 || page > MAX_PAGES)
            throw new InvalidSearchException(INVALID_SEARCH_MAX_PAGES.formatted(MAX_PAGES));
    }

    private void verifyId(Long id) {
        if (id == null) throw new InvalidSearchException(INVALID_SEARCH_ACTOR_ID);
    }
}
