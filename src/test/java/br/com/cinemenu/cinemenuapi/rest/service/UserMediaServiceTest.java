package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaUpdateMethodRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserMediaResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.UserMedia;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.domain.repository.MediaListRepository;
import br.com.cinemenu.cinemenuapi.domain.repository.UserMediaRepository;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.CineMenuEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WithMockUser
class UserMediaServiceTest {

    private UserMediaService service;
    @Mock
    private UserMediaRepository repository;
    @Mock
    private MediaListRepository mediaListRepository;

    private Pageable page;
    private UserMediaRequestDto requestDto;
    private UserMediaRequestDto.UserMediaElementsDto elementsDto;
    private UserMediaUpdateMethodRequestDto updateMethodRequestDto;
    private UserMedia userMedia;
    private UserMedia anotherUserMedia;
    private List<UserMedia> userMediaList;
    private List<UserMedia> anotherUserMediaList;
    private MediaList mediaList;
    private List<MediaList> mediaListList;
    private CineMenuUser user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new UserMediaService(repository, mediaListRepository);

        mediaListList = new ArrayList<>();
        userMediaList = new ArrayList<>();
        anotherUserMediaList = new ArrayList<>();
        elementsDto = new UserMediaRequestDto.UserMediaElementsDto(1, MediaType.MOVIE, "Gracefully note", 0.0, false);
        updateMethodRequestDto = new UserMediaUpdateMethodRequestDto(null, 10.0, true);
        requestDto = new UserMediaRequestDto(List.of(elementsDto));
        userMedia = new UserMedia(elementsDto);
        anotherUserMedia = new UserMedia(elementsDto);
        mediaList = new MediaList("ID", "Title", "Gracefully description", userMediaList, ListVisibility.PUBLIC, 0, 0, OffsetDateTime.now(), null, user);
        user = new CineMenuUser("ID", "name", "username", "email", "password", OffsetDateTime.now(), false, null, mediaListList);

        page = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 20;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return Sort.unsorted();
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }

    @Test
    @DisplayName("Test createNewUserMediaAndAddToMediaList() method whit valid signature")
    void testCreateNewUserMediaAndAddToMediaListScene01() {
        // Given
        String mediaListId = mediaList.getId();
        mediaListList.add(mediaList);
        userMediaList.add(userMedia);
        when(repository.saveAll(requestDto.medias().stream().map(UserMedia::new).toList())).thenReturn(userMediaList);

        // When
        List<UserMediaResponseDto> serviceResponse = service.createNewUserMediaAndAddToMediaList(user, mediaListId, requestDto);

        // Then
        assertEquals(2, serviceResponse.size());
        assertEquals(userMediaList.get(0).getMediaType(), serviceResponse.get(0).mediaType());
        verify(repository).saveAll(requestDto.medias().stream().map(UserMedia::new).toList());
    }

    @Test
    @DisplayName("Test createNewUserMediaAndAddToMediaList() method whit invalid signature and expect CineMenuEntityNotFoundException")
    void testCreateNewUserMediaAndAddToMediaListScene02() {
        // Given
        String invalidMediaListId = "invalid id";

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            List<UserMediaResponseDto> serviceResponse = service.createNewUserMediaAndAddToMediaList(user, invalidMediaListId, requestDto);
            assertNull(serviceResponse);
        });

    }

    @Test
    @DisplayName("Test createNewUserMediaAndAddToMediaList() method whit invalid idTmdb requestDto and expect IllegalArgumentException")
    void testCreateNewUserMediaAndAddToMediaListScene03() {
        // Given
        String mediaListId = mediaList.getId();
        mediaListList.add(mediaList);
        userMediaList.add(userMedia);
        var invalidRequestDto = new UserMediaRequestDto(List.of(new UserMediaRequestDto.UserMediaElementsDto(null, MediaType.MOVIE, "Gracefully note", 0.0, false)));

        // When // Then
        assertThrows(IllegalArgumentException.class, () -> {
            List<UserMediaResponseDto> serviceResponse = service.createNewUserMediaAndAddToMediaList(user, mediaListId, invalidRequestDto);
            assertNull(serviceResponse);
        });

    }

    @Test
    @DisplayName("Test createNewUserMediaAndAddToMediaList() method whit invalid MediaType requestDto and expect IllegalArgumentException")
    void testCreateNewUserMediaAndAddToMediaListScene04() {
        // Given
        String mediaListId = mediaList.getId();
        mediaListList.add(mediaList);
        userMediaList.add(userMedia);
        var invalidRequestDto = new UserMediaRequestDto(List.of(new UserMediaRequestDto.UserMediaElementsDto(1, null, "Gracefully note", 0.0, false)));

        // When // Then
        assertThrows(IllegalArgumentException.class, () -> {
            List<UserMediaResponseDto> serviceResponse = service.createNewUserMediaAndAddToMediaList(user, mediaListId, invalidRequestDto);
            assertNull(serviceResponse);
        });

    }

    @Test
    @DisplayName("Test editMediaByIdFromUserMediaList() method whit valid signature")
    void testEditMediaByIdFromUserMediaListScene01() {
        // Given
        String mediaId = userMedia.getId();
        mediaListList.add(mediaList);
        userMediaList.add(userMedia);
        when(repository.findById(mediaId)).thenReturn(Optional.ofNullable(userMedia));

        // When
        UserMediaResponseDto serviceResponse = service.editMediaByIdFromUserMediaList(user, mediaId, updateMethodRequestDto);

        // Then
        assertTrue(serviceResponse.watched());
        verify(repository).findById(mediaId);
    }

    @Test
    @DisplayName("Test editMediaByIdFromUserMediaList() method whit invalid signature and expect CineMenuEntityNotFoundException")
    void testEditMediaByIdFromUserMediaListScene02() {
        // Given
        String invalidMediaId = "invalid id";

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            UserMediaResponseDto serviceResponse = service.editMediaByIdFromUserMediaList(user, invalidMediaId, updateMethodRequestDto);
            assertNull(serviceResponse);
        });
    }

    @Test
    @DisplayName("Test editMediaByIdFromUserMediaList() method whit valid signature and expect CineMenuEntityNotFoundException from userMedia from another user")
    void testEditMediaByIdFromUserMediaListScene03() {
        // Given
        String mediaId = anotherUserMedia.getId();
        anotherUserMediaList.add(anotherUserMedia);
        when(repository.findById(mediaId)).thenReturn(Optional.ofNullable(anotherUserMedia));

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            UserMediaResponseDto serviceResponse = service.editMediaByIdFromUserMediaList(user, mediaId, updateMethodRequestDto);
            assertNull(serviceResponse);
        });
    }

    @Test
    @DisplayName("Test deleteById() method whit valid user and userMedia Id")
    void testDeleteByIdScene01() {
        // Given
        String mediaId = userMedia.getId();
        mediaListList.add(mediaList);
        userMediaList.add(userMedia);
        when(repository.findById(mediaId)).thenReturn(Optional.ofNullable(userMedia));

        // When
        service.deleteById(user, mediaId);

        // Then
        assertTrue(user.getMediaLists().get(0).getUserMedias().isEmpty());
        verify(repository).findById(mediaId);
    }

    @Test
    @DisplayName("Test deleteById() method whit invalid userMedia Id")
    void testDeleteByIdScene02() {
        // Given
        String invalidMediaId = "invalid id";

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            service.deleteById(user, invalidMediaId);
        });
    }

    @Test
    @DisplayName("Test deleteById() method whit valid userMedia Id but from another user")
    void testDeleteByIdScene03() {
        // Given
        String mediaId = anotherUserMedia.getId();
        anotherUserMediaList.add(anotherUserMedia);
        when(repository.findById(mediaId)).thenReturn(Optional.ofNullable(anotherUserMedia));

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            service.deleteById(user, mediaId);
        });
    }

    @Test
    @DisplayName("Test getUserMediaPagesFromListId() method and receive a Page of UserMediaResponseDto whit one element")
    void testGetUserMediaPagesFromListIdScene01() {
        // Given
        String mediaListId = mediaList.getId();
        mediaListList.add(mediaList);
        userMediaList.add(userMedia);
        when(mediaListRepository.findById(mediaListId)).thenReturn(Optional.ofNullable(mediaList));

        // When
        Page<UserMediaResponseDto> serviceResponse = service.getUserMediaPagesFromListId(user, mediaListId, page);

        //Then
        assertEquals(1, serviceResponse.getContent().size());
        verify(mediaListRepository).findById(mediaListId);
    }

    @Test
    @DisplayName("Test getUserMediaPagesFromListId() method whit invalid mediaList Id and expect CineMenuEntityNotFoundException")
    void testGetUserMediaPagesFromListIdScene02() {
        // Given
        String invalidMediaListId = "invalid ID";

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
           service.getUserMediaPagesFromListId(user, invalidMediaListId, page);
        });
    }

}