package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaUpdateMethodRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaListResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserMediaResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.MediaListService;
import br.com.cinemenu.cinemenuapi.rest.service.UserMediaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/lists")
public class MediaListController {

    private final UserRepository repository;
    private final MediaListService mediaListService;
    private final UserMediaService userMediaService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public ResponseEntity<MediaListResponseDto> create(@RequestBody @Valid MediaListRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaListService.create(getUser(), requestDto));
    }

    @PostMapping("/user_media")
    public ResponseEntity<List<UserMediaResponseDto>> createNewUserMedia(@RequestParam(name = "id_list") String mediaListId, @RequestBody @Valid UserMediaRequestDto requestDtoList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userMediaService.createNewUserMediaAndAddToMediaList(getUser(), mediaListId, requestDtoList));
    }

    @GetMapping
    public ResponseEntity<Page<MediaListResponseDto>> getListFromActualUser(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.ok(mediaListService.getAllListsFromUser(getUser(), page));
    }

    @GetMapping("/user_media")
    public ResponseEntity<Page<UserMediaResponseDto>> getUserMediaPagesFromListId(@RequestParam("id_list") String mediaListId, Pageable page) {
        return ResponseEntity.ok(userMediaService.getUserMediaPagesFromListId(getUser(), mediaListId, page));
    }

    @GetMapping("/find")
    public ResponseEntity<MediaListResponseDto> getListById(@RequestParam("id") String listId) {
        return ResponseEntity.ok(mediaListService.getListById(getUser(), listId));
    }

    @GetMapping("/user_search")
    public ResponseEntity<Page<MediaListResponseDto>> userSearchLists(@RequestParam("q") String query, Pageable page) {
        return ResponseEntity.ok(mediaListService.getUserMediaListsBySearch(getUser(),query, page));
    }

    @GetMapping("/public_search")
    public ResponseEntity<Page<MediaListResponseDto>> publicSearchLists(@RequestParam("q") String query, Pageable page) {
        return ResponseEntity.ok(mediaListService.getPublicMediaListsBySearch(query, page));
    }

    @PutMapping
    public ResponseEntity<MediaListResponseDto> edit(@RequestParam("id") String listId, @RequestBody MediaListRequestDto requestDto) {
        return ResponseEntity.ok(mediaListService.editById(getUser(), listId, requestDto));
    }

    @PutMapping("/user_media")
    public ResponseEntity<UserMediaResponseDto> editUserMediaById(@RequestParam("id_media") String mediaId, @RequestBody @Valid UserMediaUpdateMethodRequestDto updateRequestDto) {
        return ResponseEntity.ok(userMediaService.editMediaByIdFromUserMediaList(getUser(), mediaId, updateRequestDto));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteById(@RequestParam("id") String listId) {
        mediaListService.deleteById(getUser(), listId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user_media")
    public ResponseEntity<HttpStatus> deleteUserMediaById(@RequestParam("id_media") String mediaId) {
        userMediaService.deleteById(getUser(), mediaId);
        return ResponseEntity.noContent().build();
    }

    private CineMenuUser getUser() {
        return (CineMenuUser) repository.findByUsername(authenticationFacade.getAuthentication().getName());
    }
}
