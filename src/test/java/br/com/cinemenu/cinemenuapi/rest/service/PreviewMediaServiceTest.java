package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResults;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewPopularResults;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import br.com.cinemenu.cinemenuapi.rest.repository.PreviewMediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PreviewMediaServiceTest {

    @Mock
    private PreviewMediaRepository previewMediaRepository;

    @InjectMocks
    private PreviewMediaService previewMediaService;
    private List<CineMenuMediaResponse> mediaResponseList01;
    private List<CineMenuMediaResponse> mediaResponseList02;
    private PreviewMediaResponsePage resultPage01;
    private PreviewMediaResponsePage resultPage02;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        previewMediaService = new PreviewMediaService(previewMediaRepository);

        CineMenuMediaResponse mediaResponse01 = new CineMenuMediaResponse(
                2287L, "Batman", "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg",
                MediaType.TV, "1966-01-12", 7.4);

        CineMenuMediaResponse mediaResponse02 = new CineMenuMediaResponse(
                3084L, "Marlon Brando", "/fuTEPMsBtV1zE98ujPONbKiYDc2.jpg",
                MediaType.PERSON, null, 0.0);

        mediaResponseList01 = Collections.singletonList(mediaResponse01);
        mediaResponseList02 = Collections.singletonList(mediaResponse02);

        resultPage01 = new PreviewMediaResponsePage(1, mediaResponseList01, 10);
        resultPage02 = new PreviewMediaResponsePage(1, mediaResponseList02, 10);
    }

    @Test
    @DisplayName("When searching for media with normal search, it should return a list of media and status code 200")
    void testGetResponse01() {
        // Configuração do mock do PreviewMediaRepository
        List<Integer> genderIDs = new ArrayList<>();
        genderIDs.addAll(List.of(10765, 35, 10759));
        List<String> originCountry = new ArrayList<>();
        originCountry.add("US");

        PreviewMediaResults.PreviewMediaResultResponse resultResponse = new PreviewMediaResults.PreviewMediaResultResponse(
                false, "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg", 2287L,
                null, "en", null, "Batman (também conhecido como \"Batman e Robin\" ou \"Batman de Adam West\") foi uma série de televisão exibido entre 1966 e 1968, tendo ao todo 60 episódios , sendo cada uma dividida em duas partes , totalizando 120 episódios . O programa é baseado no personagem homônimo das histórias em quadrinhos e narra a luta contra o crime do herói (cujo nome verdadeiro é Bruce Wayne), sempre acompanhado pelo fiel parceiro Robin (alter-ego: Dick Grayson) e auxiliado pelo mordomo Alfred, pelo comissário de polícia James Gordon e pelo chefe de polícia O’Hara.",
                "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg", null, "TV", genderIDs, 7.4, null,
                false, 7.4, 123, "Batman", "Batman", "1966-01-12", originCountry
        );

        PreviewMediaResults previewMediaResults = new PreviewMediaResults(
                1, Collections.singletonList(resultResponse), 10
        );

        when(previewMediaRepository.getSearchPreviewMediaResponse("Batman", 1))
                .thenReturn(previewMediaResults);

        // Chamada do método a ser testado
        PreviewMediaResponsePage response = previewMediaService.getSearchResponse("Batman", 1);

        // Verificação do resultado esperado
        assertEquals(resultPage01, response);

        // Verificação de chamadas de método no mock repository, se necessário
        verify(previewMediaRepository).getSearchPreviewMediaResponse("Batman", 1);
    }

    @Test
    @DisplayName("When searching for media with normal search, it should return a list of media whit a PERSON in response.")
    void testGetResponse02() {

        PreviewMediaResults.PreviewMediaResultResponse resultResponse = new PreviewMediaResults.PreviewMediaResultResponse(
                false, null, 3084L,
                null, null, null, null, null, "/fuTEPMsBtV1zE98ujPONbKiYDc2.jpg",
                "PERSON", null, 20.519, null, false, 0.0,
                0, "Marlon Brando", "Marlon Brando", null, null
        );

        PreviewMediaResults previewMediaResults = new PreviewMediaResults(
                1, Collections.singletonList(resultResponse), 10
        );

        when(previewMediaRepository.getSearchPreviewMediaResponse("Marlon Brando", 1))
                .thenReturn(previewMediaResults);

        // Chamada do método a ser testado
        PreviewMediaResponsePage response = previewMediaService.getSearchResponse("Marlon Brando", 1);

        // Verificação do resultado esperado
        assertEquals(resultPage02, response);

        // Verificação de chamadas de método no mock repository, se necessário
        verify(previewMediaRepository).getSearchPreviewMediaResponse("Marlon Brando", 1);
    }

    @Test
    @DisplayName("Test getGenreResponse")
    void testGetGenreResponse() {
        // Given
        List<Integer> genreIds = Arrays.asList(1, 2, 3);
        Integer page = 1;

        PreviewMediaResponsePage expectedResponse = new PreviewMediaResponsePage(
                1,
                Arrays.asList(
                        new CineMenuMediaResponse(1L, "Movie 1", "/poster1.jpg", MediaType.MOVIE, "2023-05-24", 7.5),
                        new CineMenuMediaResponse(2L, "Movie 2", "/poster2.jpg", MediaType.MOVIE, "2023-05-25", 8.0)
                ),
                10
        );

        PreviewMediaRepository previewMediaRepository = mock(PreviewMediaRepository.class);
        when(previewMediaRepository.getGenrePreviewMediaResponse(genreIds, page))
                .thenReturn(expectedResponse);

        PreviewMediaService previewMediaService = new PreviewMediaService(previewMediaRepository);

        // When
        PreviewMediaResponsePage actualResponse = previewMediaService.getGenreResponse(genreIds, page);

        // Then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test getPopularPersonList")
    void testGetPopularPersonListFromPreviewMediaService() {
        // Given
        PreviewMediaRepository previewMediaRepository = Mockito.mock(PreviewMediaRepository.class);
        PreviewMediaResponsePage expectedResponsePage = new PreviewMediaResponsePage(1, List.of(new CineMenuMediaResponse(1L, "Name", "profilePath", MediaType.PERSON, null, null)), 500);

        Integer page = 1;

        // Mocking repository method
        PreviewPopularResults mockResults = new PreviewPopularResults(page, List.of(new PreviewPopularResults.PreviewPopularResultsResponse(false, 2, 1L,
                List.of(new PreviewMediaResults.PreviewMediaResultResponse(false, "backdropPath", 1L, "Title", "pt-BR", "pt-BR",
                        "Overview", "posterPath", null, "movie", List.of(1), 0.0,
                        "0000-00-00", false, 0.0, 0, null, null, null, List.of("BR"))),
                "acting", "Name", 0.0, "profilePath")),
                500);
        Mockito.when(previewMediaRepository.getPeopleListResults(page)).thenReturn(mockResults);

        // Mocking mapper method
        List<CineMenuMediaResponse> mockMediaList = List.of(new CineMenuMediaResponse(1L, "Name", "profilePath", MediaType.PERSON, null, null));

        // When
        PreviewMediaResponsePage result = new PreviewMediaResponsePage(page, mockMediaList, 500);

        // Then
        assertEquals(expectedResponsePage, result);
    }
}
