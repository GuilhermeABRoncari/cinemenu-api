package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.*;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.rest.repository.PreviewMediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PreviewMediaServiceTest {
    @InjectMocks
    private PreviewMediaService previewMediaService;

    @Mock
    private PreviewMediaRepository previewMediaRepository;
    PreviewMovieDetailsResultDto previewMovieDetailsResultDto;
    PreviewMovieCreditsResultDto previewMovieCreditsResultDto;
    PreviewMovieWatchProvidersResultDto previewMovieWatchProvidersResultDto;
    PreviewMovieVideoResultDto previewMovieVideoResultDto;

    PreviewTvShowDetailsResultDto previewTvShowDetailsResultDto;
    PreviewTvShowCreditsResultDto previewTvShowCreditsResultDto;
    PreviewTvShowWatchProvidersResultDto previewTvShowWatchProvidersResultDto;
    PreviewTvShowVideoResultDto previewTvShowVideoResultDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Boolean adult = false;
        String backdropPath = "backdrop_path.com";
        String posterPath = "poster_path.com";
        String profilePath = "profile_path.com";
        String logoPath = "logo_path.com";
        String homePage = "home_page.com";
        String link = "link.com";
        String site = "site.com";
        String name = "name";
        String originalName = "Original Name";
        String imdbId = "imdbId";
        String originalLanguage = "pt-BR";
        String country = "BR";
        String title = "Title";
        String originalTitle = "Original Title";
        String originalCountry = "BR";
        String overview = "Gracefully Overview";
        String releaseDate = "0000-00-00";
        String knowForDepartmentOf = "Acting";
        String department = "Directing";
        String job = "Director";
        String character = "character";
        String creditId = "credit_id";
        String key = "key0001";
        Double popularity = 10.0;
        Double voteAverage = 10.0;
        Integer id = 1;
        Integer gender = 1;
        Integer runtime = 60;
        Integer voteCount = 10;
        Integer order = 1;
        Integer displayPriority = 0;
        Integer episodeNumber = 1;
        Integer seasonNumber = 1;
        Long budget = 1000000L;
        Long revenue = 10000000L;
        String englishName = "Portuguese";
        String status = "status";
        String tagLine = "tagLine";
        String type = "Trailer";
        Boolean video = true;
        Boolean official = true;
        Boolean inProduction = false;


        PreviewMovieWatchProvidersResultDto.Flatrate movieFlatrate = new PreviewMovieWatchProvidersResultDto.Flatrate(logoPath, id, name, displayPriority);
        PreviewMovieWatchProvidersResultDto.Buy movieBuy = new PreviewMovieWatchProvidersResultDto.Buy(logoPath, id, name, displayPriority);
        PreviewMovieWatchProvidersResultDto.Free movieFree = new PreviewMovieWatchProvidersResultDto.Free(logoPath, id, name, displayPriority);
        PreviewTvShowWatchProvidersResultDto.Flatrate tvFlatrate = new PreviewTvShowWatchProvidersResultDto.Flatrate(logoPath, id, name, displayPriority);
        PreviewTvShowWatchProvidersResultDto.Buy tvBuy = new PreviewTvShowWatchProvidersResultDto.Buy(logoPath, id, name, displayPriority);
        PreviewTvShowWatchProvidersResultDto.Free tvFree = new PreviewTvShowWatchProvidersResultDto.Free(logoPath, id, name, displayPriority);

        PreviewMovieDetailsResultDto.BelongsToCollection belongsToCollection = new PreviewMovieDetailsResultDto.BelongsToCollection(id, name, posterPath, backdropPath);
        PreviewMovieDetailsResultDto.Genre movieGenre = new PreviewMovieDetailsResultDto.Genre(id, name);
        PreviewTvShowDetailsResultDto.Genre tvGenre = new PreviewTvShowDetailsResultDto.Genre(id, name);
        PreviewMovieDetailsResultDto.ProductionCompany movieProductionCompany = new PreviewMovieDetailsResultDto.ProductionCompany(id, logoPath, name, originalCountry);
        PreviewTvShowDetailsResultDto.ProductionCompany tvShowProductionCompany = new PreviewTvShowDetailsResultDto.ProductionCompany(id, logoPath, name, originalCountry);
        PreviewMovieDetailsResultDto.ProductionCountry productionCountry = new PreviewMovieDetailsResultDto.ProductionCountry("iso3166_1", name);
        PreviewMovieDetailsResultDto.SpokenLanguage movieSpokenLanguage = new PreviewMovieDetailsResultDto.SpokenLanguage(englishName, "iso639_1", name);
        PreviewTvShowDetailsResultDto.SpokenLanguage tvShowSpokenLanguage = new PreviewTvShowDetailsResultDto.SpokenLanguage(englishName, "iso639_1", name);
        PreviewMovieCreditsResultDto.CastMember movieCastMember = new PreviewMovieCreditsResultDto.CastMember(adult, gender, id, knowForDepartmentOf, name, originalName, popularity, profilePath, character, creditId, order);
        PreviewTvShowCreditsResultDto.CastMember tvCastMember = new PreviewTvShowCreditsResultDto.CastMember(adult, gender, id, knowForDepartmentOf, name, originalName, popularity, profilePath, character, creditId, order);
        PreviewMovieCreditsResultDto.CrewMember movieCrewMember = new PreviewMovieCreditsResultDto.CrewMember(adult, gender, id, knowForDepartmentOf, name, originalName, popularity, profilePath, department, job, creditId);
        PreviewTvShowCreditsResultDto.CrewMember tvCrewMember = new PreviewTvShowCreditsResultDto.CrewMember(adult, gender, (long) id, knowForDepartmentOf, name, originalName, popularity, profilePath, department, job, creditId);
        PreviewMovieWatchProvidersResultDto.WatchProvider movieWatchProvider = new PreviewMovieWatchProvidersResultDto.WatchProvider(link, List.of(movieFlatrate), List.of(movieBuy), List.of(movieFree));
        PreviewTvShowWatchProvidersResultDto.WatchProvider tvWatchProvider = new PreviewTvShowWatchProvidersResultDto.WatchProvider(link, List.of(tvFlatrate), List.of(tvBuy), List.of(tvFree));
        PreviewMovieVideoResultDto.VideoResult movieVideoResult = new PreviewMovieVideoResultDto.VideoResult("iso_639_1", "iso_3166_1", name, key, site, 1, type, official, releaseDate, "id");
        PreviewTvShowVideoResultDto.VideoResult tvVideoResult = new PreviewTvShowVideoResultDto.VideoResult("iso_639_1", "iso_3166_1", name, key, site, 1, type, official, releaseDate, "id");
        PreviewTvShowDetailsResultDto.Episode episode = new PreviewTvShowDetailsResultDto.Episode(id, name, overview, voteAverage, voteCount, releaseDate, episodeNumber, type, status, runtime, 1, id, "path");
        PreviewTvShowDetailsResultDto.Network network = new PreviewTvShowDetailsResultDto.Network(id, logoPath, name, originalCountry);
        PreviewTvShowDetailsResultDto.Season season = new PreviewTvShowDetailsResultDto.Season(releaseDate, episodeNumber, id, name, overview, posterPath, seasonNumber, voteCount);

        previewMovieDetailsResultDto = new PreviewMovieDetailsResultDto(
                adult, backdropPath, belongsToCollection, budget, List.of(movieGenre), homePage, (long) id, imdbId, originalLanguage,
                originalTitle, overview, popularity, posterPath, List.of(movieProductionCompany), List.of(productionCountry), releaseDate,
                revenue, runtime, List.of(movieSpokenLanguage), status, tagLine, title, video, voteAverage, voteCount
        );
        previewMovieCreditsResultDto = new PreviewMovieCreditsResultDto(id, List.of(movieCastMember), List.of(movieCrewMember));
        previewMovieWatchProvidersResultDto = new PreviewMovieWatchProvidersResultDto(id, Map.of(country, movieWatchProvider));
        previewMovieVideoResultDto = new PreviewMovieVideoResultDto(id, List.of(movieVideoResult));

        previewTvShowDetailsResultDto = new PreviewTvShowDetailsResultDto(
                adult, backdropPath, List.of(), List.of(runtime), releaseDate, List.of(tvGenre), homePage, (long) id, inProduction,
                List.of(originalLanguage), releaseDate, episode, name, 2, List.of(network), episodeNumber, seasonNumber,
                List.of(originalCountry), originalLanguage, originalName, overview, popularity, posterPath, List.of(tvShowProductionCompany),
                List.of(originalCountry), List.of(season), List.of(tvShowSpokenLanguage), status, tagLine, type, voteAverage, voteCount
        );
        previewTvShowCreditsResultDto = new PreviewTvShowCreditsResultDto(id, List.of(tvCastMember), List.of(tvCrewMember));
        previewTvShowWatchProvidersResultDto = new PreviewTvShowWatchProvidersResultDto(id, Map.of(country, tvWatchProvider));
        previewTvShowVideoResultDto = new PreviewTvShowVideoResultDto(id, List.of(tvVideoResult));
    }

    @Test
    @DisplayName("Test getSearchResponse whit valid search and page")
    void testGetSearchResponseWithValidSearchAndPageShouldReturnPreviewMediaResponsePage() {
        // Given
        String search = "test";
        Integer page = 1;

        List<PreviewMediaResults.PreviewMediaResultResponse> results = new ArrayList<>();
        results.add(new PreviewMediaResults.PreviewMediaResultResponse(
                false, null, 1L, "Title", "pt-BR", "Original Title",
                "overview", "posterPath", null, "movie", List.of(1, 2, 3),
                0.0, "0000-00-00", false, 0.0, 0, null, null,
                null, List.of("BR")
        ));
        PreviewMediaResults previewMediaResults = new PreviewMediaResults(page, results, 2);

        when(previewMediaRepository.getSearchPreviewMediaResponse(eq(search), eq(page))).thenReturn(previewMediaResults);

        // When
        PreviewMediaResponsePage response = previewMediaService.getSearchResponse(search, page);

        // Then
        assertNotNull(response);
        assertEquals(page, response.page());
        assertEquals(1, response.results().size());
        assertEquals(2, response.totalPages());

        verify(previewMediaRepository, times(1)).getSearchPreviewMediaResponse(eq(search), eq(page));
    }

    @Test
    @DisplayName("Test getGenreResponse whit valid id and page")
    void testGetGenreResponseWithValidGenreIdAndPageShouldReturnPreviewMediaResponsePage() {
        // Given
        List<Integer> genreId = List.of(1, 2, 3);
        List<CineMenuMediaResponse> results = new ArrayList<>();
        Integer page = 1;

        PreviewMediaResponsePage expectedResponse = new PreviewMediaResponsePage(page, results, 1);
        when(previewMediaRepository.getGenrePreviewMediaResponse(genreId, page)).thenReturn(expectedResponse);

        // When
        PreviewMediaResponsePage response = previewMediaService.getGenreResponse(genreId, page);

        // Then
        assertNotNull(response);
        assertSame(expectedResponse, response);

        verify(previewMediaRepository, times(1)).getGenrePreviewMediaResponse(genreId, page);
    }

    @Test
    @DisplayName("Test getPopularPeopleList whit valid page")
    void testGetPopularPeopleListWithValidPageShouldReturnPreviewMediaResponsePage() {
        // Given
        Integer page = 1;
        Integer totalPages = 2;
        List<PreviewPopularResults.PreviewPopularResultsResponse> results = new ArrayList<>();

        PreviewPopularResults previewPopularResults = new PreviewPopularResults(page, results, totalPages);

        when(previewMediaRepository.getPeopleListResults(page)).thenReturn(previewPopularResults);

        // When
        PreviewMediaResponsePage response = previewMediaService.getPopularPeopleList(page);

        // Then
        assertNotNull(response);
        assertEquals(page, response.page());
        assertEquals(0, response.results().size());
        assertEquals(2, response.totalPages());

        verify(previewMediaRepository, times(1)).getPeopleListResults(page);
    }

    @Test
    @DisplayName("Test getMovieListByActor whit valid id")
    void testGetMovieListByActorWithValidIdShouldReturnPreviewMediaResponsePage() {
        // Given
        Long id = 1L;
        List<PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse> results = new ArrayList<>();

        PreviewActorCreditsListResults previewActorCreditsListResults = new PreviewActorCreditsListResults(results, id);

        when(previewMediaRepository.getMovieListByActorId(id)).thenReturn(previewActorCreditsListResults);

        // When
        PreviewMediaResponsePage response = previewMediaService.getMovieListByActor(id);

        // Then
        assertNotNull(response);
        assertEquals(0, response.results().size());

        verify(previewMediaRepository, times(1)).getMovieListByActorId(id);
    }

    @Test
    @DisplayName("Test method getSeriesListByActor whit valid id")
    void testGetSeriesListByActorWithValidIdShouldReturnPreviewMediaResponsePage() {
        // Given
        Long id = 1L;
        List<PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse> results = new ArrayList<>();

        PreviewActorCreditsListResults previewActorCreditsListResults = new PreviewActorCreditsListResults(results, id);

        when(previewMediaRepository.getSeriesListByActorId(id)).thenReturn(previewActorCreditsListResults);

        // When
        PreviewMediaResponsePage response = previewMediaService.getSeriesListByActor(id);

        // Then
        assertNotNull(response);
        assertEquals(0, response.results().size());

        verify(previewMediaRepository, times(1)).getSeriesListByActorId(id);
    }

    @Test
    @DisplayName("Test getSimilarByIdAndMedia whit valid id, tv media and page")
    void testGetSimilarByIdAndMediaWithValidIdAndMediaAndPageShouldReturnPreviewMediaResponsePage() {
        // Given
        Long id = 1L;
        MediaType media = MediaType.TV;
        List<PreviewMediaResults.PreviewMediaResultResponse> results = new ArrayList<>();
        Integer page = 1;
        Integer totalPages = 2;

        PreviewMediaResults previewMediaResults = new PreviewMediaResults(page, results, totalPages);

        when(previewMediaRepository.getSimilarTVShowListById(id, page)).thenReturn(previewMediaResults);

        // When
        PreviewMediaResponsePage response = previewMediaService.getSimilarByIdAndMedia(id, media, page);

        // Then
        assertNotNull(response);
        assertEquals(page, response.page());
        assertEquals(0, response.results().size());
        assertEquals(2, response.totalPages());

        verify(previewMediaRepository, times(1)).getSimilarTVShowListById(id, page);
    }

    @Test
    @DisplayName("Test getSimilarByIdAndMedia whit valid id, movie media and page")
    void testGetSimilarByIdAndMedia_WithValidIdAndMediaAndPage_ShouldReturnPreviewMediaResponsePage02() {
        // Given
        Long id = 1L;
        MediaType media = MediaType.MOVIE;
        List<PreviewMediaResults.PreviewMediaResultResponse> results = new ArrayList<>();
        Integer page = 1;
        Integer totalPages = 2;

        PreviewMediaResults previewMediaResults = new PreviewMediaResults(page, results, totalPages);

        when(previewMediaRepository.getSimilarMovieListById(id, page)).thenReturn(previewMediaResults);

        // When
        PreviewMediaResponsePage response = previewMediaService.getSimilarByIdAndMedia(id, media, page);

        // Then
        assertNotNull(response);
        assertEquals(page, response.page());
        assertEquals(0, response.results().size());
        assertEquals(2, response.totalPages());

        verify(previewMediaRepository, times(1)).getSimilarMovieListById(id, page);
    }

    @Test
    @DisplayName("Test getSimilarByIdAndMedia whit invalid media")
    void testGetSimilarByIdAndMedia_WithInvalidMedia_ShouldThrowInvalidSearchException() {
        // Given
        Long id = 1L;
        MediaType media = MediaType.PERSON;
        Integer page = 1;

        // When / Then
        assertThrows(InvalidSearchException.class, () -> previewMediaService.getSimilarByIdAndMedia(id, media, page));

        verify(previewMediaRepository, never()).getSimilarTVShowListById(anyLong(), anyInt());
        verify(previewMediaRepository, never()).getSimilarMovieListById(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Test getMediaDetail method whit valid MediaType(MOVIE) and Id")
    void getMediaDetailTestScene01() {
        // Given
        Long validMovieId = 1L;
        MediaType media = MediaType.MOVIE;
        when(previewMediaRepository.getMovieDetailsById(validMovieId)).thenReturn(previewMovieDetailsResultDto);
        when(previewMediaRepository.getMovieCreditsById(validMovieId)).thenReturn(previewMovieCreditsResultDto);
        when(previewMediaRepository.getMovieWatchProvidersById(validMovieId)).thenReturn(previewMovieWatchProvidersResultDto);
        when(previewMediaRepository.getMovieVideosById(validMovieId)).thenReturn(previewMovieVideoResultDto);

        // When
        MediaDetailsResultResponseDto mediaDetail = previewMediaService.getMediaDetail(media, validMovieId);

        // Then
        assertEquals("Title", mediaDetail.title());

        verify(previewMediaRepository).getMovieDetailsById(validMovieId);
        verify(previewMediaRepository).getMovieCreditsById(validMovieId);
        verify(previewMediaRepository).getMovieWatchProvidersById(validMovieId);
        verify(previewMediaRepository).getMovieVideosById(validMovieId);
    }

    @Test
    @DisplayName("Test getMediaDetail method whit valid MediaType(TV) and Id")
    void getMediaDetailTestScene02() {
        // Given
        Long validTvShowId = 1L;
        MediaType media = MediaType.TV;
        when(previewMediaRepository.getTvShowDetailsById(validTvShowId)).thenReturn(previewTvShowDetailsResultDto);
        when(previewMediaRepository.getTvShowCreditsById(validTvShowId)).thenReturn(previewTvShowCreditsResultDto);
        when(previewMediaRepository.getTvShowWatchProvidersById(validTvShowId)).thenReturn(previewTvShowWatchProvidersResultDto);
        when(previewMediaRepository.getTvShowVideosById(validTvShowId)).thenReturn(previewTvShowVideoResultDto);

        // When
        MediaDetailsResultResponseDto mediaDetail = previewMediaService.getMediaDetail(media, validTvShowId);

        // Then
        assertEquals("name", mediaDetail.title());

        verify(previewMediaRepository).getTvShowDetailsById(validTvShowId);
        verify(previewMediaRepository).getTvShowCreditsById(validTvShowId);
        verify(previewMediaRepository).getTvShowWatchProvidersById(validTvShowId);
        verify(previewMediaRepository).getTvShowVideosById(validTvShowId);
    }

    @Test
    @DisplayName("Test getMediaDetail whit invalid MediaType(PERSON)")
    void getMediaDetailScene03() {
        // Given
        Long validId = 1L;
        MediaType invalidMediaType = MediaType.PERSON;

        // When // Then
        assertThrows(InvalidSearchException.class, () -> {
            previewMediaService.getMediaDetail(invalidMediaType, validId);
        });

        verify(previewMediaRepository, never()).getMovieDetailsById(validId);
        verify(previewMediaRepository, never()).getTvShowDetailsById(validId);
        verify(previewMediaRepository, never()).getMovieCreditsById(validId);
        verify(previewMediaRepository, never()).getTvShowCreditsById(validId);
        verify(previewMediaRepository, never()).getMovieWatchProvidersById(validId);
        verify(previewMediaRepository, never()).getTvShowWatchProvidersById(validId);
        verify(previewMediaRepository, never()).getMovieVideosById(validId);
        verify(previewMediaRepository, never()).getTvShowVideosById(validId);
    }
}
