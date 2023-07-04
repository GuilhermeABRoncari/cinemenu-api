package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.rest.service.PreviewMediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CineMenuPreviewMediaControllerTest {

    @InjectMocks
    private CineMenuPreviewMediaController controller;
    private String search;
    @Mock
    private PreviewMediaService service;
    private CineMenuMediaResponse mediaResponse;
    private List<CineMenuMediaResponse> mediaResponseList = new ArrayList<>();
    private PreviewMediaResponsePage result;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new CineMenuPreviewMediaController(service);
        search = "Batman";

        mediaResponse = new CineMenuMediaResponse(
                2287L, "Batman", "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg",
                MediaType.TV, "1966-01-12", 7.4);

        mediaResponseList.add(mediaResponse);

        result = new PreviewMediaResponsePage(1, mediaResponseList, 1);
    }

    @Test
    @DisplayName("When search is normal, should return list of media and status code 200")
    void searchMedia() {
        //Given
        Mockito.when(service.getSearchResponse(search, 1)).thenReturn(result);

        //When
        ResponseEntity<PreviewMediaResponsePage> listResponseEntity = controller.searchMedia(search, 1);

        //Then
        assertEquals(result, listResponseEntity.getBody());
        assertEquals(ResponseEntity.ok(result), listResponseEntity);
        assertEquals(listResponseEntity.getStatusCodeValue(), ResponseEntity.ok(listResponseEntity.getBody()).getStatusCodeValue());
    }

    @Test
    @DisplayName("Test mediaByGenre with valid genreId and page")
    void testMediaByGenreWithValidInputs() {
        // Given
        List<Integer> genreIds = Arrays.asList(89, 76); // Example internal cine-menu genre IDs
        int page = 1;

        // When
        ResponseEntity<PreviewMediaResponsePage> responseEntity = controller.mediaByGenre(genreIds, page);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test popularPeopleList end point")
    void popularPeopleListTest() {
        // Given
        Integer page = 1;

        // When
        ResponseEntity<PreviewMediaResponsePage> responseEntity = controller.popularPeopleList(page);
    }

    @DisplayName("Test moviesByActor and expect http code 200")
    void moviesListByActorIdTestScene01() {
        // Given
        Long chrisPrattId = 73457L;

        // When
        var responseEntity = controller.movieListByActorId(chrisPrattId);


        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test seriesByActor and expect http code 200")
    void seriesListByActorIdTestScene01() {
        // Given
        Long chrisPrattId = 73457L;

        // When
        var responseEntity = controller.seriesListByActorId(chrisPrattId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test similarByMedia whit MediaType MOVIE and expect status code 200")
    void similarByMediaAndIdTest() {
        // Given
        Long id = 12L;
        MediaType media = MediaType.MOVIE;
        Integer page = 1;

        // When
        var responseEntity = controller.similarByIdAndMedia(id, media, page);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}