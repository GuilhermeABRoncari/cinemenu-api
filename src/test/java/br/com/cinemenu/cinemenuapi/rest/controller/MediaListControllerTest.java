package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaListResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.MediaListService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WithMockUser
class MediaListControllerTest {

    private MediaListController controller;
    @Mock
    private UserRepository repository;
    @Mock
    private MediaListService service;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private Authentication authentication;
    @Mock
    private Pageable page;

    private MediaListRequestDto postRequestDto;
    private MediaList mediaList;
    private MediaListResponseDto responseDto;
    private CineMenuUser user;
    private Page<MediaListResponseDto> pageResponseDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationFacade = new AuthenticationFacade() {
            @Override
            public Authentication getAuthentication() {
                return authentication;
            }
        };
        controller = new MediaListController(repository, service, authenticationFacade);

        user = new CineMenuUser("Id", "Name", "Username", "example@email.com", "password", OffsetDateTime.now(), false, null, List.of(new MediaList()));
        postRequestDto = new MediaListRequestDto("Title", "Description", ListVisibility.PUBLIC);
        mediaList = new MediaList(postRequestDto, user);
        responseDto = new MediaListResponseDto(mediaList);
        pageResponseDto = new PageImpl<>(List.of(responseDto));
    }

    @Test
    @DisplayName("Test method create() in endpoint /lists whit Http verb POST and expect HttpStatus 201 CREATED")
    void testPostScene01() {
        // Given
        when(service.create(user, postRequestDto)).thenReturn(responseDto);

        // When
        ResponseEntity<MediaListResponseDto> controllerResponse = controller.create(postRequestDto);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method getListFromActualUser() in endpoint /lists and expect HttpStatus 200 OK ")
    void testGetScene01() {
        // Given
        when(service.getAllListsFromUser(user, page)).thenReturn(pageResponseDto);

        // When
        ResponseEntity<Page<MediaListResponseDto>> controllerResponse = controller.getListFromActualUser(page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method getListById() in endpoint /lists and expect HttpStatus 200 OK ")
    void testGetScene02() {
        // Given
        when(service.getListById(user, mediaList.getId())).thenReturn(responseDto);

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
        when(service.getUserMediaListsBySearch(user, query, page)).thenReturn(pageResponseDto);

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
        when(service.getPublicMediaListsBySearch(query, page)).thenReturn(pageResponseDto);

        // When
        ResponseEntity<Page<MediaListResponseDto>> controllerResponse = controller.publicSearchLists(query, page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method edit() in endpoint /lists and expect HttpStatus 200 OK ")
    void testPutScene01() {
        // Given
        when(service.editById(user, mediaList.getId(), postRequestDto)).thenReturn(responseDto);

        // When
        ResponseEntity<MediaListResponseDto> controllerResponse = controller.edit(mediaList.getId(), postRequestDto);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method delete() in endpoint /lists and expect HttpStatus 204 OK ")
    void testDeleteScene01() {
        // Given
        String listId = mediaList.getId();

        // When
        ResponseEntity<HttpStatus> controllerResponse = controller.deleteById(listId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, controllerResponse.getStatusCode());
    }
}