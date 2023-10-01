package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaListResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import br.com.cinemenu.cinemenuapi.domain.repository.MediaListRepository;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.CineMenuEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MediaListServiceTest {

    private MediaListService service;
    @Mock
    private MediaListRepository repository;

    private Pageable page;
    private MediaListRequestDto publicRequestDto;
    private MediaListRequestDto privateRequestDto;
    private MediaList publicMediaList;
    private MediaList privateMediaList;
    private MediaListResponseDto publicResponseDto;
    private MediaListResponseDto privateResponseDto;
    private List<MediaList> listOfMediaLists;
    private List<MediaList> anotherUserListOfMediaLists;
    private CineMenuUser user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new MediaListService(repository);
        listOfMediaLists = new ArrayList<>();
        UserProfile userProfile = new UserProfile("bio");
        user = new CineMenuUser("id", userProfile,"name", "username", "example@email.com", "password", OffsetDateTime.now(), false, null, listOfMediaLists);

        publicRequestDto = new MediaListRequestDto("title", "description", ListVisibility.PUBLIC);
        privateRequestDto = new MediaListRequestDto("title", "description", ListVisibility.PRIVATE);
        publicMediaList = new MediaList(publicRequestDto, user);
        privateMediaList = new MediaList(privateRequestDto, user);
        publicResponseDto = new MediaListResponseDto(publicMediaList);
        privateResponseDto = new MediaListResponseDto(privateMediaList);
        anotherUserListOfMediaLists = new ArrayList<>();

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
    @DisplayName("Test method create() in service class whit valid credentials")
    void testCreateMethod01() {
        // Given
        when(repository.save(publicMediaList)).thenReturn(publicMediaList);

        // When
        MediaListResponseDto serviceResponse = service.create(user, publicRequestDto);

        // Then
        assertEquals(publicResponseDto, serviceResponse);
        assertEquals(1, user.getMediaLists().size());
        assertTrue(user.getMediaLists().contains(publicMediaList));
        verify(repository).save(publicMediaList);
    }

    @Test
    @DisplayName("Test method getAllListsFromUser() in service class whit empty MediaList")
    void testGetAllListsFromUserMethod01() {
        // When
        Page<MediaListResponseDto> serviceResponse = service.getAllListsFromUser(user, page);

        // Then
        assertTrue(serviceResponse.isEmpty());
    }

    @Test
    @DisplayName("Test method getAllListsFromUser() in service class whit populated MediaList")
    void testGetAllListsFromUserMethod02() {
        // Given
        listOfMediaLists.add(publicMediaList);
        listOfMediaLists.add(privateMediaList);

        // When
        Page<MediaListResponseDto> serviceResponse = service.getAllListsFromUser(user, page);

        // Then
        assertEquals(2, serviceResponse.getContent().size());
    }

    @Test
    @DisplayName("Test method getListById() in service class whit valid listId")
    void testGetListByIdMethod01() {
        // Given
        String listId = publicMediaList.getId();
        when(repository.findById(listId)).thenReturn(Optional.ofNullable(publicMediaList));

        // When
        MediaListResponseDto serviceResponse = service.getListById(user, listId);

        // Then
        assertEquals(publicResponseDto, serviceResponse);
        verify(repository).findById(listId);
    }

    @Test
    @DisplayName("Test method getListById() in service class whit private listId and expect CineMenuEntityNotFoundException")
    void testGetListByIdMethod02() {
        // Given
        String listId = privateMediaList.getId();
        when(repository.findById(listId)).thenReturn(Optional.ofNullable(privateMediaList));

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            MediaListResponseDto serviceResponse = service.getListById(user, listId);
            assertNull(serviceResponse);
        });
        verify(repository).findById(listId);
    }

    @Test
    @DisplayName("Test method getListById() in service class whit invalid listId and expect CineMenuEntityNotFoundException")
    void testGetListByIdMethod03() {
        // Given
        String invalidListId = "invalidId";
        when(repository.findById(invalidListId)).thenThrow(CineMenuEntityNotFoundException.class);

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            MediaListResponseDto serviceResponse = service.getListById(user, invalidListId);
            assertNull(serviceResponse);
        });
        verify(repository).findById(invalidListId);
    }

    @Test
    @DisplayName("Test method getUserMediaListsBySearch() in service class whit valid query")
    void testGetUserMediaListsBySearchMethod01() {
        // Given
        String query = "title";
        listOfMediaLists.add(publicMediaList);
        listOfMediaLists.add(privateMediaList);
        when(repository.findAllByTitleLikeIgnoreCase("%" + query + "%")).thenReturn(listOfMediaLists);

        // When
        Page<MediaListResponseDto> serviceResponse = service.getUserMediaListsBySearch(user, query, page);

        // Then
        assertEquals(2, serviceResponse.getContent().size());
        assertTrue(serviceResponse.getContent().contains(privateResponseDto));
        verify(repository).findAllByTitleLikeIgnoreCase("%" + query + "%");
    }

    @Test
    @DisplayName("Test method getUserMediaListsBySearch() in service class whit invalid query")
    void testGetUserMediaListsBySearchMethod02() {
        // Given
        String invalidQuery = "123456789NotFoundTest";
        when(repository.findAllByTitleLikeIgnoreCase("%" + invalidQuery + "%")).thenReturn(listOfMediaLists);

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            Page<MediaListResponseDto> serviceResponse = service.getUserMediaListsBySearch(user, invalidQuery, page);
            assertNull(serviceResponse);
        });
        verify(repository).findAllByTitleLikeIgnoreCase("%" + invalidQuery + "%");
    }

    @Test
    @DisplayName("Test method editById() in service class whit valid Id")
    void testEditByIdMethod01() {
        // Given
        String listId = publicMediaList.getId();
        listOfMediaLists.add(publicMediaList);
        when(repository.findById(listId)).thenReturn(Optional.ofNullable(publicMediaList));

        // When
        MediaListResponseDto serviceResponse = service.editById(user, listId, privateRequestDto);

        // Then
        assertNotEquals(privateResponseDto, serviceResponse);
        assertEquals(publicMediaList.getVisibility(), serviceResponse.visibility());
        assertEquals(publicMediaList.getLastChange(), serviceResponse.lastChange());
        verify(repository).findById(listId);
    }

    @Test
    @DisplayName("Test method editById() in service class whit invalid Id and expect CineMenuEntityNotFoundException")
    void testEditByIdMethod02() {
        // Given
        String invalidListId = "invalidId";
        when(repository.findById(invalidListId)).thenThrow(CineMenuEntityNotFoundException.class);

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            MediaListResponseDto serviceResponse = service.editById(user, invalidListId, privateRequestDto);
            assertNull(serviceResponse);
        });
        verify(repository).findById(invalidListId);
    }

    @Test
    @DisplayName("Test method editById() in service class whit valid Id and expect CineMenuEntityNotFoundException for MediaList not owned by this user")
    void testEditByIdMethod03() {
        // Given
        String listId = publicMediaList.getId();
        when(repository.findById(listId)).thenReturn(Optional.ofNullable(publicMediaList));

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            MediaListResponseDto serviceResponse = service.editById(user, listId, privateRequestDto);
            assertNull(serviceResponse);
            assertTrue(user.getMediaLists().isEmpty());
        });
        verify(repository).findById(listId);
    }

    @Test
    @DisplayName("Test method deleteById() in service class whit valid Id")
    void testDeleteByIdMethod01() {
        // Given
        String listId = publicMediaList.getId();
        listOfMediaLists.add(publicMediaList);
        when(repository.findById(listId)).thenReturn(Optional.ofNullable(publicMediaList));

        // When
        service.deleteById(user, listId);

        // Then
        assertTrue(listOfMediaLists.isEmpty());
        verify(repository).findById(listId);
    }

    @Test
    @DisplayName("Test method deleteById() in service class whit invalid Id and expect CineMenuEntityNotFoundException")
    void testDeleteByIdMethod02() {
        // Given
        String invalidListId = "invalidId";
        when(repository.findById(invalidListId)).thenThrow(CineMenuEntityNotFoundException.class);

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            service.deleteById(user, invalidListId);
        });
        verify(repository).findById(invalidListId);
    }

    @Test
    @DisplayName("Test method deleteById() in service class whit valid Id and expect CineMenuEntityNotFoundException for MediaList not owned by this user")
    void testDeleteByIdMethod03() {
        // Given
        String listId = publicMediaList.getId();
        anotherUserListOfMediaLists.add(publicMediaList);
        when(repository.findById(listId)).thenReturn(Optional.ofNullable(publicMediaList));

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            service.deleteById(user, listId);
            assertNotNull(anotherUserListOfMediaLists);
        });
        verify(repository).findById(listId);
    }

    @Test
    @DisplayName("Test getPublicMediaListsBySearch() method whit valid query")
    void testGetPublicPublicMediaListsBySearchMethod01() {
        // Given
        String query = "tit";
        anotherUserListOfMediaLists.add(publicMediaList);
        anotherUserListOfMediaLists.add(privateMediaList);
        when(repository.findAllByTitleLikeIgnoreCase("%" + query + "%")).thenReturn(anotherUserListOfMediaLists);

        // When
        Page<MediaListResponseDto> serviceResponse = service.getPublicMediaListsBySearch(query, page);

        // Then
        assertEquals(1, serviceResponse.getContent().size());
        assertFalse(serviceResponse.getContent().contains(privateResponseDto));
        verify(repository).findAllByTitleLikeIgnoreCase("%" + query + "%");
    }

    @Test
    @DisplayName("Test getPublicMediaListsBySearch() method whit invalid query and expect CineMenuEntityNotFoundException")
    void testGetPublicPublicMediaListsBySearchMethod02() {
        // Given
        String invalidQuery = "invalidQuery";
        when(repository.findAllByTitleLikeIgnoreCase("%" + invalidQuery + "%")).thenReturn(listOfMediaLists);

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            service.getPublicMediaListsBySearch(invalidQuery, page);
        });
        verify(repository).findAllByTitleLikeIgnoreCase("%" + invalidQuery + "%");
    }

    @Test
    @DisplayName("Test getPublicMediaListsBySearch() method whit valid query and expect CineMenuEntityNotFoundException because all results have private visibility")
    void testGetPublicPublicMediaListsBySearchMethod03() {
        // Given
        String query = "tit";
        listOfMediaLists.add(privateMediaList);
        when(repository.findAllByTitleLikeIgnoreCase("%" + query + "%")).thenReturn(listOfMediaLists);

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            service.getPublicMediaListsBySearch(query, page);
        });
        verify(repository).findAllByTitleLikeIgnoreCase("%" + query + "%");
    }
}