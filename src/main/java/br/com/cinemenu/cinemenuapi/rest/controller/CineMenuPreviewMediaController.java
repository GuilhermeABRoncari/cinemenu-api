package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaSearchDTO;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.rest.service.PreviewMediaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinemenu")
@AllArgsConstructor
public class CineMenuPreviewMediaController {

    private final PreviewMediaService service;

    @PostMapping("/search/page={page}")
    public ResponseEntity<List<CineMenuMediaResponse>> searchMedia(@RequestBody @Valid MediaSearchDTO search, @PathVariable int page) {
        return ResponseEntity.ok(service.getResponse(search.search(), page));
    }

}
