package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.rest.service.PreviewMediaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinemenu")
@AllArgsConstructor
public class CineMenuPreviewMediaController {

    private final PreviewMediaService service;

    @GetMapping("/search")
    public ResponseEntity<PreviewMediaResponsePage> searchMedia(@RequestParam("q") String search, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getSearchResponse(search, page));
    }

    @GetMapping("/genre")
    public ResponseEntity<PreviewMediaResponsePage> mediaByGenre(@RequestParam("id") List<Integer> genreId, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getGenreResponse(genreId, page));
    }

    @GetMapping("/popular")
    public ResponseEntity<PreviewMediaResponsePage> popularPeopleList(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getPopularPeopleList(page));
    }

    @GetMapping("/moviesByActor")
    public ResponseEntity<PreviewMediaResponsePage> movieListByActorId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(service.getMovieListByActor(id));
    }

    @GetMapping("/seriesByActor")
    public ResponseEntity<PreviewMediaResponsePage> seriesListByActorId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(service.getSeriesListByActor(id));
    }

    @GetMapping("/similarByMedia")
    public ResponseEntity<PreviewMediaResponsePage> similarByIdAndMedia(
            @RequestParam("id") Long id, @RequestParam("media") MediaType media, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getSimilarByIdAndMedia(id, media, page));
    }
}
