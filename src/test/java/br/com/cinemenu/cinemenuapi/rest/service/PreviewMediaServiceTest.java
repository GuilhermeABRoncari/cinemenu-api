package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.rest.repository.PreviewMediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PreviewMediaServiceTest {

    @Mock
    private PreviewMediaRepository previewMediaRepository;

    @InjectMocks
    private PreviewMediaService previewMediaService;

    private List<CineMenuMediaResponse> mediaResponseList;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        previewMediaService = new PreviewMediaService(previewMediaRepository);

        CineMenuMediaResponse mediaResponse = new CineMenuMediaResponse(
                2287, "Batman", "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg",
                MediaType.TV, "1966-01-12", 7.4);

        mediaResponseList = Collections.singletonList(mediaResponse);
    }

    @Test
    @DisplayName("When searching for media with normal search, it should return a list of media and status code 200")
    void testGetResponse() {
        // Configuração do mock do PreviewMediaRepository
        List<Integer> genderIDs = new ArrayList<>();
        genderIDs.addAll(List.of(10765, 35, 10759));
        List<String> originCountry = new ArrayList<>();
        originCountry.add("US");

        PreviewMediaResponse.PreviewMediaResultResponse resultResponse = new PreviewMediaResponse.PreviewMediaResultResponse(
                        false, "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg", 2287,
                        null, "en", null, "Batman (também conhecido como \"Batman e Robin\" ou \"Batman de Adam West\") foi uma série de televisão exibido entre 1966 e 1968, tendo ao todo 60 episódios , sendo cada uma dividida em duas partes , totalizando 120 episódios . O programa é baseado no personagem homônimo das histórias em quadrinhos e narra a luta contra o crime do herói (cujo nome verdadeiro é Bruce Wayne), sempre acompanhado pelo fiel parceiro Robin (alter-ego: Dick Grayson) e auxiliado pelo mordomo Alfred, pelo comissário de polícia James Gordon e pelo chefe de polícia O’Hara.",
                "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg", null, "TV", genderIDs, 7.4, null,
                        false, 7.4, 123, "Batman", "Batman","1966-01-12", originCountry
                );

        PreviewMediaResponse previewMediaResponse = new PreviewMediaResponse(
                1, Collections.singletonList(resultResponse), 10
        );

        when(previewMediaRepository.getPreviewMediaResponse("Batman", 1))
                .thenReturn(previewMediaResponse);

        // Chamada do método a ser testado
        List<CineMenuMediaResponse> response = previewMediaService.getResponse("Batman", 1);

        // Verificação do resultado esperado
        assertEquals(mediaResponseList, response);

        // Verificação de chamadas de método no mock repository, se necessário
        verify(previewMediaRepository).getPreviewMediaResponse("Batman", 1);
    }
}
