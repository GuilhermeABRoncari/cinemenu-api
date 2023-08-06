package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaListResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.MediaListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/lists")
public class MediaListController {

    private final UserRepository repository;
    private final MediaListService service;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public ResponseEntity<MediaListResponseDto> create(@RequestBody @Valid MediaListRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(getUser(), requestDto));
    }

    @GetMapping
    public ResponseEntity<Page<MediaListResponseDto>> getListFromActualUser(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.ok(service.getAllListsFromUser(getUser(), page));
    }

    @GetMapping("/find")
    public ResponseEntity<MediaListResponseDto> getListById(@RequestParam("id") String listId) {
        return ResponseEntity.ok(service.getListById(getUser(), listId));
    }

    @GetMapping("/user_search")
    public ResponseEntity<Page<MediaListResponseDto>> userSearchLists(@RequestParam("q") String query, Pageable page) {
        return ResponseEntity.ok(service.getUserMediaListsBySearch(getUser(),query, page));
    }

    @GetMapping("/public_search")
    public ResponseEntity<Page<MediaListResponseDto>> publicSearchLists(@RequestParam("q") String query, Pageable page) {
        return ResponseEntity.ok(service.getPublicMediaListsBySearch(query, page));
    }

    @PutMapping
    public ResponseEntity<MediaListResponseDto> edit(@RequestParam("id") String listId, @RequestBody MediaListRequestDto requestDto) {
        return ResponseEntity.ok(service.editById(getUser(), listId, requestDto));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteById(@RequestParam("id") String listId) {
        service.deleteById(getUser(), listId);
        return ResponseEntity.noContent().build();
    }

    private CineMenuUser getUser() {
        return (CineMenuUser) repository.findByUsername(authenticationFacade.getAuthentication().getName());
    }
}
