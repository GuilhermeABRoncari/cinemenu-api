package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaSearchDTO;
import br.com.cinemenu.cinemenuapi.rest.service.TMDBService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinemenu")
@AllArgsConstructor
public class CineMenuController {

    @PostMapping("/search")
    public ResponseEntity<String> searchMedia(@RequestBody @Valid MediaSearchDTO search) {
        return ResponseEntity.ok(new TMDBService().getResponse(search.search(), search.page()));
    }

}
