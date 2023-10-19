package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.*;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserPreferencesResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserProfileResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.CineMenuEntityNotFoundException;
import br.com.cinemenu.cinemenuapi.infra.security.SecurityConfigurations;
import br.com.cinemenu.cinemenuapi.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;

import java.time.OffsetDateTime;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CineMenuUserServiceTest {

    private CineMenuUserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfigurations securityConfigurations;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;
    private CineMenuUser validUser;
    @Mock
    private CineMenuUser regularUser;
    private UserProfile userProfile;
    @Mock
    private PreviewMediaService mediaService;
    private AccountDeleteRequestDto accountDeleteRequestDto;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        securityConfigurations = Mockito.mock(SecurityConfigurations.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        tokenService = Mockito.mock(TokenService.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        mediaService = Mockito.mock(PreviewMediaService.class);
        regularUser = Mockito.mock(CineMenuUser.class);
        userService = new CineMenuUserService(userRepository, securityConfigurations, tokenService, authenticationManager, mediaService);
        when(securityConfigurations.passwordEncoder()).thenReturn(passwordEncoder);

        userProfile = new UserProfile("Bio", List.of(CineMenuGenres.ADVENTURE), Map.of(12L, MediaType.MOVIE));
        validUser = new CineMenuUser(
                "Id", userProfile, "Name", "Username", "example@email.com",
                "password", OffsetDateTime.now(), false, null, List.of());
        regularUser = new CineMenuUser("Id", userProfile, "Name", "Username", "example@email.com",
                "password", OffsetDateTime.now(), false, null, List.of());
    }

    @Test
    @DisplayName("Test login with valid credentials")
    void testLoginWithValidCredentialsShouldReturnTokenResponseDto() throws AuthenticationException {
        // Given
        LoginRequestDto loginDto = new LoginRequestDto("email@example.com", "Test123*");
        String token = "valid_token";
        UserProfile userProfile = new UserProfile("bio", List.of(), Map.of());
        CineMenuUser user = new CineMenuUser(null, userProfile, "name", "teste", "email@example.com", "Test123*", null, false, null, List.of(new MediaList()));
        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(user)).thenReturn(token);
        when(userRepository.getReferenceByEmail(loginDto.email())).thenReturn(user);

        // When
        var response = userService.login(loginDto);

        // Then
        assertNotNull(response);
        assertEquals(token, response.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(user);
        verify(userRepository).getReferenceByEmail(loginDto.email());
    }

    @Test
    @DisplayName("Test login with invalid credentials")
    void testLoginWithInvalidCredentialsShouldThrowIllegalArgumentException() {
        // Given
        LoginRequestDto loginDto = new LoginRequestDto("email@example.com", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(IllegalArgumentException.class);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(loginDto));
    }

    @Test
    @DisplayName("Test sign method with existing email")
    void testSignWithExistingEmail() {
        // Given
        CineMenuUserRequestDto userDto = new CineMenuUserRequestDto("name", "username", "email@example.com", "password", "password");
        when(userRepository.existsByEmail(userDto.email())).thenReturn(true);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.sign(userDto));
        verify(userRepository, never()).existsByUsername(anyString());
        verify(userRepository, never()).save(any(CineMenuUser.class));
    }

    @Test
    @DisplayName("Test sign method with existing username")
    void testSignWithExistingUsername() {
        // Given
        CineMenuUserRequestDto userDto = new CineMenuUserRequestDto("name", "username", "email@example.com", "password", "password");
        when(userRepository.existsByEmail(userDto.email())).thenReturn(false);
        when(userRepository.existsByUsername(userDto.username())).thenReturn(true);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.sign(userDto));
        verify(userRepository).existsByEmail(anyString());
        verify(userRepository, never()).save(any(CineMenuUser.class));
    }

    @Test
    @DisplayName("Test  deleteUserAccount method with valid user and email")
    void testDeleteUserAccountScene01() {
        // Given
        accountDeleteRequestDto = new AccountDeleteRequestDto("example@email.com");

        // When
        userService.deleteUserAccount(validUser, accountDeleteRequestDto);

        // Then
        verify(userRepository, times(1)).delete(validUser);
    }

    @Test
    @DisplayName("Test  deleteUserAccount method with invalid user and email")
    void testDeleteUserAccountScene02() {
        // Given
        accountDeleteRequestDto = new AccountDeleteRequestDto("invalidEmail@example.com");

        // When // Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUserAccount(validUser, accountDeleteRequestDto);
        });
        verify(userRepository, times(0)).delete(validUser);
    }

    @Test
    @DisplayName("Test getUserProfile method")
    void testGetUserProfile() {
        // Given
        // When
        var serviceResponse = userService.getUserProfile(validUser);
        // Then
        assertEquals(UserProfileResponseDto.class, serviceResponse.getClass());
        assertEquals("Name", serviceResponse.name());
    }

    @Test
    @DisplayName("Test getUserProfileById method with valid Id")
    void testGetUserProfileByIdScene01() {
        // Given
        String id = "Id";
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(validUser));

        // When
        var serviceResponse = userService.getUserProfileById(id);

        // Then
        assertEquals(UserProfileResponseDto.class, serviceResponse.getClass());
        assertEquals("Name", serviceResponse.name());
    }

    @Test
    @DisplayName("Test getUserProfileById method with invalid Id")
    void testGetUserProfileByIdScene02() {
        // Given
        String invalidId = "invalidId";

        // When // Then
        assertThrows(CineMenuEntityNotFoundException.class, () -> {
            userService.getUserProfileById(invalidId);
        });

        verify(userRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Test updateUserProfile method whit valid credentials")
    void testUpdateUserProfileScene01() {
        // Given
        UserProfileRequestDto requestDto = new UserProfileRequestDto("NewName", "NewUsername", "NewBio");
        when(userRepository.save(validUser)).thenReturn(validUser);

        // When
        var serviceResponse = userService.updateUserProfile(validUser, requestDto);

        // Then
        assertEquals(UserProfileResponseDto.class, serviceResponse.getClass());
        assertNotEquals("Name", validUser.getName());
        assertNotEquals("Username", validUser.getUsername());
        assertNotEquals("Bio", validUser.getProfile().getBiography());
    }

    @Test
    @DisplayName("Test updateUserProfile method whit invalid credentials")
    void testUpdateUserProfileScene02() {
        // Given
        UserProfileRequestDto invalidRequestDto = new UserProfileRequestDto(null, "Username", "NewBio");
        when(userRepository.existsByUsername(invalidRequestDto.username())).thenReturn(true);

        // When // Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserProfile(validUser, invalidRequestDto);
        });

        verify(userRepository, times(1)).existsByUsername(invalidRequestDto.username());
    }
}
