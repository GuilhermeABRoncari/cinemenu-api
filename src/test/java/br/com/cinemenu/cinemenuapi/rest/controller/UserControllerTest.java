package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.AccountDeleteRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserPreferencesRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserPreferencesResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserProfileResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.CineMenuUserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WithMockUser
@AllArgsConstructor
class UserControllerTest {

    private UserController controller;
    @Mock
    private UserRepository repository;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private Authentication authentication;
    @Mock
    private CineMenuUserService service;
    private CineMenuUser validUser;
    private AccountDeleteRequestDto validAccountDeleteRequestDto;
    private UserProfileRequestDto userProfileRequestDto;
    private UserProfileResponseDto userProfileResponseDto;
    private Map<Long, MediaType> mapReference = new HashMap<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationFacade = new AuthenticationFacade() {
            @Override
            public Authentication getAuthentication() {
                return authentication;
            }
        };
        controller = new UserController(repository, authenticationFacade, service);

        String id = "validId";
        String name = "name";
        String validEmail = "validEmail@example.com";
        String username = "username";
        String password = "password";
        validAccountDeleteRequestDto = new AccountDeleteRequestDto(validEmail);
        UserProfile userProfile = new UserProfile("validBiography", List.of(), mapReference);
        validUser = new CineMenuUser(id, userProfile, name, username, validEmail, password, OffsetDateTime.now(), false, null, List.of(new MediaList()));

        userProfileRequestDto = new UserProfileRequestDto("new name", "newUsername", "bio");
        userProfileResponseDto = new UserProfileResponseDto(validUser);
    }

    @Test
    @DisplayName("Test method getUserProfile() from UserController")
    void testGetUserProfile() {
        // Given
        when(service.getUserProfile(validUser)).thenReturn(userProfileResponseDto);

        // When
        ResponseEntity<UserProfileResponseDto> controllerResponse = controller.getUserProfile();

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method getUserProfileById() from UserController")
    void testGetUserProfileById() {
        // Given
        when(service.getUserProfileById(validUser.getId())).thenReturn(userProfileResponseDto);

        // Then
        ResponseEntity<UserProfileResponseDto> controllerResponse = controller.getUserProfileById(validUser.getId());

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        verify(service).getUserProfileById(validUser.getId());
    }

    @Test
    @DisplayName("Test method updateUserProfile from UserController")
    void testUpdateUserProfile() {
        // Given
        // When
        ResponseEntity<UserProfileResponseDto> controllerResponse = controller.updateUserProfile(userProfileRequestDto);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method delete from UserController whit valid credentials")
    void testAccountDelete() {
        // Given
        when(repository.findByUsername(validUser.getUsername())).thenReturn(validUser);
        when(authenticationFacade.getAuthentication().getName()).thenReturn(validUser.getUsername());

        // When
        ResponseEntity<HttpStatus> response = controller.delete(validAccountDeleteRequestDto);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repository).findByUsername(validUser.getUsername());
    }

    @Test
    @DisplayName("Test method setUserPreferences() from UserController whit valid UserPreferencesRequestDto")
    void testSetUserPreferencesScene01() {
        // Given
        List<UserPreferencesRequestDto.CineMenuGenresId> genreList = new ArrayList<>(Collections.singleton(new UserPreferencesRequestDto.CineMenuGenresId(CineMenuGenres.CRIME.getCineMenuGenreId())));
        List<UserPreferencesRequestDto.UserTMDBMediaRequestReference> mediaReferences = Collections.singletonList(new UserPreferencesRequestDto.UserTMDBMediaRequestReference(1396L, MediaType.TV));
        UserPreferencesRequestDto requestDto = new UserPreferencesRequestDto(genreList, mediaReferences);

        // When
        ResponseEntity<UserPreferencesResponseDto> controllerResponse = controller.setUserPreferences(requestDto);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test method getRecommendationsByUserPreferences whit valid page number")
    void testGetRecommendationsByUserPreferences() {
        // Given
        Integer page = 1;

        // When
        ResponseEntity<PreviewMediaResponsePage> controllerResponse = controller.getRecommendationsByUserPreferences(page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
    }

}