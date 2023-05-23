package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaSearchDTO;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.rest.service.PreviewMediaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinemenu")
@AllArgsConstructor
public class CineMenuPreviewMediaController {

    private final PreviewMediaService service;
    @PostMapping("/search")
    public ResponseEntity<List<CineMenuMediaResponse>> searchMedia(@RequestBody @Valid MediaSearchDTO search) {
        return ResponseEntity.ok(service.getResponse(search.search(), search.page()));
    }

}
