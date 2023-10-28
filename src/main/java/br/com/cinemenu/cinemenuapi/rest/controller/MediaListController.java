package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaUpdateMethodRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaListResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserMediaResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.MediaListService;
import br.com.cinemenu.cinemenuapi.rest.service.UserMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/lists")
@SecurityRequirement(name = "bearerAuth")
public class MediaListController {

    private final UserRepository repository;
    private final MediaListService mediaListService;
    private final UserMediaService userMediaService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    @Operation(
            summary = "Create user media list.",
            description = """
                    Create a list to input user medias references! 
                    This list can be shared, forked, copied, liked, deleted, updated in his descriptions, name and visibility.
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "A new media list has been successfully created.")
            }
    )
    public ResponseEntity<MediaListResponseDto> create(@RequestBody @Valid MediaListRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaListService.create(getUser(), requestDto));
    }

    @PostMapping("/user_media")
    @Operation(
            summary = "Add a new media reference.",
            description = """
                    Add new media reference to existing media list for the current user.
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "A new media has been added.")
            },
            parameters = {
                    @Parameter(name = "id_list", description = "A string UUID from an existing media list owned by the current user.", required = true)
            }
    )
    public ResponseEntity<List<UserMediaResponseDto>> createNewUserMedia(@RequestParam(name = "id_list") String mediaListId, @RequestBody @Valid UserMediaRequestDto requestDtoList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userMediaService.createNewUserMediaAndAddToMediaList(getUser(), mediaListId, requestDtoList));
    }

    @PostMapping("/fork")
    @Operation(
            summary = "Copy a list.",
            description = """
                    Copies the list provided by ID to the current user, removing all personal notes from the original user.
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "The fork has been successfully made.")
            },
            parameters = {
                    @Parameter(name = "id", description = "A string UUID from an existing list by another user.", required = true)
            }
    )
    public ResponseEntity<MediaListResponseDto> copyListById(@RequestParam("id") String id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaListService.copyListById(getUser(), id));
    }

    @GetMapping
    @Operation(
            summary = "Get a page of media lists of current user.",
            description = """
                    Get a page of media lists of current user using parameters like page, size, and sort.
                    By default, the initial index of the page is based on 0 (zero).
                    By default, this endpoint is sorted by created_at.DESC.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "page", description = "Page number.", required = true),
                    @Parameter(name = "sort", description = "Change the sort criterion.", required = false),
                    @Parameter(name = "size", description = "Change the number of elements for page.", required = false)
            }
    )
    public ResponseEntity<Page<MediaListResponseDto>> getListFromActualUser(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.ok(mediaListService.getAllListsFromUser(getUser(), page));
    }

    @GetMapping("/user_media")
    @Operation(
            summary = "Get a page of media by a list id.",
            description = """
                    Gets pagination of media by the list ID provided. 
                    By default, lists can only be accessed if they belong to the current user, or have public visibility.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id_list", description = "ID of an existing media list.", required = true),
                    @Parameter(name = "page", description = "Page number.", required = true),
                    @Parameter(name = "sort", description = "Change the sort criterion.", required = false),
                    @Parameter(name = "size", description = "Change the number of elements for page.", required = false)
            }
    )
    public ResponseEntity<Page<UserMediaResponseDto>> getUserMediaPagesFromListId(@RequestParam("id_list") String mediaListId, Pageable page) {
        return ResponseEntity.ok(userMediaService.getUserMediaPagesFromListId(getUser(), mediaListId, page));
    }

    @GetMapping("/find")
    @Operation(
            summary = "Return a media list by ID.",
            description = "Return a media list by a existing media list belonging of the current user or if the list have a public visibility.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID of an existing media list.", required = true)
            }
    )
    public ResponseEntity<MediaListResponseDto> getListById(@RequestParam("id") String listId) {
        return ResponseEntity.ok(mediaListService.getListById(getUser(), listId));
    }

    @GetMapping("/user_search")
    @Operation(
            summary = "Search for a list from the current user.",
            description = """
                    Search for a list title contains the provided query from the current user, getting a page of results.
                    By default, the initial index of the page is based on 0 (zero).
                    Its possible use parameters like size, and sort.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "q", description = "Query used to search.", required = true),
                    @Parameter(name = "page", description = "Page number.", required = true),
                    @Parameter(name = "sort", description = "Change the sort criterion.", required = false),
                    @Parameter(name = "size", description = "Change the number of elements for page.", required = false)
            }
    )
    public ResponseEntity<Page<MediaListResponseDto>> userSearchLists(@RequestParam("q") String query, Pageable page) {
        return ResponseEntity.ok(mediaListService.getUserMediaListsBySearch(getUser(), query, page));
    }

    @GetMapping("/public_search")
    @Operation(
            summary = "Search for a public list.",
            description = """
                    Search for a public list title contains the provided query, getting a page of results.
                    By default, the initial index of the page is based on 0 (zero).
                    Its possible use parameters like size, and sort.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "q", description = "Query used to search.", required = true),
                    @Parameter(name = "page", description = "Page number.", required = true),
                    @Parameter(name = "sort", description = "Change the sort criterion.", required = false),
                    @Parameter(name = "size", description = "Change the number of elements for page.", required = false)
            }
    )
    public ResponseEntity<Page<MediaListResponseDto>> publicSearchLists(@RequestParam("q") String query, Pageable page) {
        return ResponseEntity.ok(mediaListService.getPublicMediaListsBySearch(query, page));
    }

    @PutMapping
    @Operation(
            summary = "Edit a list by ID.",
            description = """
                    Edit a list by the provided ID.
                    Is only possible to edit a list if the list belongs to current user.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID of an existing media list belonging to current user.", required = true)
            }
    )
    public ResponseEntity<MediaListResponseDto> edit(@RequestParam("id") String listId, @RequestBody MediaListRequestDto requestDto) {
        return ResponseEntity.ok(mediaListService.editById(getUser(), listId, requestDto));
    }

    @PutMapping("/user_media")
    @Operation(
            summary = "Edit a media by ID.",
            description = """
                    Edit a media by the provided ID.
                    Is only possible to edit a media if the media belongs to current user.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id_media", description = "ID of an existing media belonging to current user.", required = true)
            }
    )
    public ResponseEntity<UserMediaResponseDto> editUserMediaById(@RequestParam("id_media") String mediaId, @RequestBody @Valid UserMediaUpdateMethodRequestDto updateRequestDto) {
        return ResponseEntity.ok(userMediaService.editMediaByIdFromUserMediaList(getUser(), mediaId, updateRequestDto));
    }

    @DeleteMapping
    @Operation(
            summary = "Delete a media list by ID.",
            description = """
                    Delete a media list by provided ID.
                    Is only possible to delete this media list if the list belongs to current user.
                    """,
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID of a media list belonging of the current user.", required = true)
            }
    )
    public ResponseEntity<HttpStatus> deleteById(@RequestParam("id") String listId) {
        mediaListService.deleteById(getUser(), listId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user_media")
    @Operation(
            summary = "Delete a media by ID.",
            description = """
                    Delete a media by provided ID.
                    Is only possible to delete this media if the media belongs to current user.
                    """,
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID of a media belonging of the current user.", required = true)
            }
    )
    public ResponseEntity<HttpStatus> deleteUserMediaById(@RequestParam("id_media") String mediaId) {
        userMediaService.deleteById(getUser(), mediaId);
        return ResponseEntity.noContent().build();
    }

    private CineMenuUser getUser() {
        return (CineMenuUser) repository.findByUsername(authenticationFacade.getAuthentication().getName());
    }
}
