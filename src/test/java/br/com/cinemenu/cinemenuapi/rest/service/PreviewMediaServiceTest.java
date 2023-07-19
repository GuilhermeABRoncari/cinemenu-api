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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PreviewMediaServiceTest {
    @InjectMocks
    private PreviewMediaService previewMediaService;

    @Mock
    private PreviewMediaRepository previewMediaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
}
