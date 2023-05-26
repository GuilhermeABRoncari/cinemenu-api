package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaSearchDTO;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.rest.service.PreviewMediaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class CineMenuPreviewMediaControllerTest {

    @InjectMocks
    private CineMenuPreviewMediaController controller;
    @Mock
    private PreviewMediaService service;
    private MediaSearchDTO searchDTO;
    private CineMenuMediaResponse mediaResponse;
    private List<CineMenuMediaResponse> mediaResponseList = new ArrayList<>();


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new CineMenuPreviewMediaController(service);
        searchDTO = new MediaSearchDTO("Batman");

        mediaResponse = new CineMenuMediaResponse(
                2287, "Batman", "/5GOO4GqoBZE6GOQ1SLFM6tNwfYo.jpg",
                MediaType.TV, "1966-01-12", 7.4);

        mediaResponseList.add(mediaResponse);
    }

    @Test
    @DisplayName("When search is normal, should return list of media and status code 200")
    void searchMedia() {
        Mockito.when(service.getResponse(searchDTO.search(), 1)).thenReturn(mediaResponseList);

        ResponseEntity<List<CineMenuMediaResponse>> listResponseEntity = controller.searchMedia(searchDTO, 1);

        Assertions.assertEquals(mediaResponseList, listResponseEntity.getBody());
        Assertions.assertEquals(ResponseEntity.ok(mediaResponseList), listResponseEntity);
        Assertions.assertEquals(listResponseEntity.getStatusCodeValue(),ResponseEntity.ok(listResponseEntity.getBody()).getStatusCodeValue());
    }
}