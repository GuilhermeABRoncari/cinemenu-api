package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.rest.service.PreviewMediaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinemenu")
@AllArgsConstructor
public class CineMenuPreviewMediaController {

    private final PreviewMediaService service;

    @GetMapping("/search={search}/page={page}")
    public ResponseEntity<List<CineMenuMediaResponse>> searchMedia(@PathVariable String search, @PathVariable int page) {
        return ResponseEntity.ok(service.getResponse(search, page));
    }

}
