package br.com.cinemenu.cinemenuapi.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CineMenuApiApplicationTest {

    @Autowired
    private CineMenuPreviewMediaController cineMenuPreviewMediaController;

//    @Test
//    @DisplayName("Deve retornar uma lista de filmes baseado na busca")
//    void searchEndPointTestScene01() {
//        MediaSearchDTO mediaSearchDTO = new MediaSearchDTO("batman", 1);
//
//        ResponseEntity<String> responseEntity = cineMenuController.searchMedia(mediaSearchDTO);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("Deve retornar erro 400 por lançar uma pesquisa em branco")
//    void searchEndPointTestScene02() {
//        MediaSearchDTO mediaSearchDTO = new MediaSearchDTO("", 1);
//
//        // Verifica se a exceção InvalidSearchException é lançada
//        assertThrows(InvalidSearchException.class, () -> {
//            ResponseEntity responseEntity= cineMenuController.searchMedia(mediaSearchDTO);
//            Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//            assertThat(responseEntity.getBody()).isEqualTo("invalid search or page");
//        });
//    }
//
//    @Test
//    @DisplayName("Deve retornar erro 400 por lançar uma pagina em branco")
//    void searchEndPointTestScene05() {
//        MediaSearchDTO mediaSearchDTO = new MediaSearchDTO("batman", null);
//
//        // Verifica se a exceção InvalidSearchException é lançada
//        assertThrows(InvalidSearchException.class, () -> {
//            cineMenuController.searchMedia(mediaSearchDTO);
//        });
//    }
//
//    @Test
//    @DisplayName("Deve retornar erro pois string search e int page está nula")
//    void searchEndPointTestScene03() {
//        TMDBService tmdbService = new TMDBService();
//        String search = "";
//        Integer page = null;
//
//        // Verifica se a exceção RuntimeException é lançada
//        assertThrows(RuntimeException.class, () -> {
//            tmdbService.getResponse(search, page);
//        });
//    }
//
//    @Test
//    @DisplayName("Deve retornar erro 500 por que a apikey está nula")
//    void searchEndPointTestScene04() {
//        TMDBService tmdbService = new TMDBService();
//        tmdbService.apiKey = null;
//
//        // Verifica se a exceção InvalidApiKeyException é lançada
//        assertThrows(InvalidApiKeyException.class, () -> {
//            tmdbService.getResponse("batman", 1);
//        });
//    }

}
