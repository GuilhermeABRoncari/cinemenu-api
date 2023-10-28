package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaUpdateMethodRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaListResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserMediaResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.UserMedia;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.MediaListService;
import br.com.cinemenu.cinemenuapi.rest.service.UserMediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@WithMockUser
class MediaListControllerTest {

    private MediaListController controller;
    @Mock
    private UserRepository repository;
    @Mock
    private MediaListService mediaListService;
    @Mock
    private UserMediaService userMediaService;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private Authentication authentication;
    @Mock
    private Pageable page;

    private MediaListRequestDto mediaListRequestDto;
    private MediaList mediaList;
    private MediaListResponseDto mediaListResponseDto;
    private CineMenuUser user;
    private Page<MediaListResponseDto> mediaListResponseDtos;
    private UserMediaRequestDto userMediaRequestDto;
    private UserMediaRequestDto.UserMediaElementsDto userMediaElementsDto;
    private UserMedia userMedia;
    private UserMediaUpdateMethodRequestDto updateMethodRequestDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationFacade = new AuthenticationFacade() {
            @Override
            public Authentication getAuthentication() {
                return authentication;
            }
        };
        controller = new MediaListController(repository, mediaListService, userMediaService, authenticationFacade);

        UserProfile userProfile = new UserProfile("bio", List.of(), Map.of());

        user = new CineMenuUser("Id", userProfile,"Name", "Username", "example@email.com", "password", OffsetDateTime.now(), false, null, List.of(new MediaList()));
        mediaListRequestDto = new MediaListRequestDto("Title", "Description", ListVisibility.PUBLIC);
        mediaList = new MediaList(mediaListRequestDto, user);
        mediaListResponseDto = new MediaListResponseDto(mediaList);
        mediaListResponseDtos = new PageImpl<>(List.of(mediaListResponseDto));
        userMediaElementsDto = new UserMediaRequestDto.UserMediaElementsDto(1, MediaType.MOVIE, "Gracefully note", 0.0, false);
        updateMethodRequestDto = new UserMediaUpdateMethodRequestDto(null, 0.0, true);
        userMedia = new UserMedia(userMediaElementsDto);
        userMediaRequestDto = new UserMediaRequestDto(List.of(userMediaElementsDto));
    }

    @Test
    @DisplayName("Test method create() in endpoint /lists whit Http verb POST and expect HttpStatus 201 CREATED")
    void testPostScene01() {
        // Given
        when(mediaListService.create(user, mediaListRequestDto)).thenReturn(mediaListResponseDto);

        // When
        ResponseEntity<MediaListResponseDto> controllerResponse = controller.create(mediaListRequestDto);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method getListFromActualUser() in endpoint /lists and expect HttpStatus 200 OK ")
    void testGetScene01() {
        // Given
        when(mediaListService.getAllListsFromUser(user, page)).thenReturn(mediaListResponseDtos);

        // When
        ResponseEntity<Page<MediaListResponseDto>> controllerResponse = controller.getListFromActualUser(page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method getListById() in endpoint /lists and expect HttpStatus 200 OK ")
    void testGetScene02() {
        // Given
        when(mediaListService.getListById(user, mediaList.getId())).thenReturn(mediaListResponseDto);

        // When
        ResponseEntity<MediaListResponseDto> controllerResponse = controller.getListById(mediaList.getId());

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method userSearchLists() in endpoint /lists and expect HttpStatus 200 OK ")
    void testGetScene03() {
        // Given
        String query = "title";
        when(mediaListService.getUserMediaListsBySearch(user, query, page)).thenReturn(mediaListResponseDtos);

        // When
        ResponseEntity<Page<MediaListResponseDto>> controllerResponse = controller.userSearchLists(query, page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method publicSearchLists() in endpoint /lists and expect HttpStatus 200 OK ")
    void testGetScene04() {
        // Given
        String query = "title";
        when(mediaListService.getPublicMediaListsBySearch(query, page)).thenReturn(mediaListResponseDtos);

        // When
        ResponseEntity<Page<MediaListResponseDto>> controllerResponse = controller.publicSearchLists(query, page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method edit() in endpoint /lists and expect HttpStatus 200 OK ")
    void testPutScene01() {
        // Given
        when(mediaListService.editById(user, mediaList.getId(), mediaListRequestDto)).thenReturn(mediaListResponseDto);

        // When
        ResponseEntity<MediaListResponseDto> controllerResponse = controller.edit(mediaList.getId(), mediaListRequestDto);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method delete() in endpoint /lists and expect HttpStatus 204 NO CONTENT ")
    void testDeleteScene01() {
        // Given
        String listId = mediaList.getId();

        // When
        ResponseEntity<HttpStatus> controllerResponse = controller.deleteById(listId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("test createNewUserMedia() method whit valid credentials and expect HttpStatus 201 CREATED")
    void testCreateNewUserMediaScene01() {
        // Given
        String listId = mediaList.getId();

        // When
        ResponseEntity<List<UserMediaResponseDto>> controllerResponse = controller.createNewUserMedia(listId, userMediaRequestDto);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("test getUserMediaPagesFromListId() method expect HttpStatus 200 OK")
    void testGetUserMediaPagesFromListIdScene01() {
        // Given
        String listId = mediaList.getId();

        // When
        ResponseEntity<Page<UserMediaResponseDto>> controllerResponse = controller.getUserMediaPagesFromListId(listId, page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("test editUserMediaById() method expect HttpStatus 200 OK")
    void testEditUserMediaByIdScene01() {
        // Given
        String mediaId = userMedia.getId();

        // When
        ResponseEntity<UserMediaResponseDto> controllerResponse = controller.editUserMediaById(mediaId, updateMethodRequestDto);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("test deleteUserMediaById() method expect HttpStatus 204 NO CONTENT")
    void testDeleteUserMediaByIdScene01() {
        // Given
        String mediaId = userMedia.getId();

        // When
        ResponseEntity<HttpStatus> controllerResponse = controller.deleteUserMediaById(mediaId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test copyListById() method expecting HttpStatus 201 CREATED")
    void testCopyListByIdScene01() {
        // Given
        String validListId = mediaList.getId();

        // When
        ResponseEntity<MediaListResponseDto> controllerResponse = controller.copyListById(validListId);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test getPublicListsByUserId() method whit valid user id")
    void testGetPublicListsByUserId() {
        // Given
        String validUserId = user.getId();

        // When
        ResponseEntity<Page<MediaListResponseDto>> controllerResponse = controller.getPublicListsByUserId(validUserId, page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }
}