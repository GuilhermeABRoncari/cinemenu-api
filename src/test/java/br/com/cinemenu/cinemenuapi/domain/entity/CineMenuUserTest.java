package br.com.cinemenu.cinemenuapi.domain.entity;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserPreferencesRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.user.UserProfile;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CineMenuUserTest {

    @Test
    @DisplayName("Test CineMenuUser entity")
    void testCineMenuUserEntity() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();
        List<CineMenuGenres> genreList = new ArrayList<>();
        genreList.add(CineMenuGenres.ACTION);
        Map<Long, MediaType> mapReference = new HashMap<>();
        mapReference.put(12L, MediaType.MOVIE);
        UserProfile userProfile = new UserProfile("bio", genreList, mapReference);

        // When
        CineMenuUser cineMenuUser = new CineMenuUser(id, userProfile,name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        // Then
        assertEquals(id, cineMenuUser.getId());
        assertEquals(userProfile, cineMenuUser.getProfile());
        assertEquals(name, cineMenuUser.getName());
        assertEquals(username, cineMenuUser.getUsername());
        assertEquals(email, cineMenuUser.getEmail());
        assertEquals(password, cineMenuUser.getPassword());
        assertEquals(registrationDate, cineMenuUser.getRegistrationDate());
        assertEquals(genreList, cineMenuUser.getProfile().getGenrePreferences());
        assertEquals(mapReference, cineMenuUser.getProfile().getTmdbMediaReferences());
        Assertions.assertTrue(cineMenuUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
        Assertions.assertTrue(cineMenuUser.isAccountNonExpired());
        Assertions.assertTrue(cineMenuUser.isAccountNonLocked());
        Assertions.assertTrue(cineMenuUser.isCredentialsNonExpired());
        Assertions.assertTrue(cineMenuUser.isEnabled());
    }

    @Test
    @DisplayName("Test CineMenuUser entity EqualsAndHashcode of id")
    void testCineMenuUserEntity02() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();
        UserProfile userProfile = new UserProfile("bio", List.of(), Map.of());

        // When
        CineMenuUser cineMenuUser01 = new CineMenuUser(id, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        CineMenuUser cineMenuUser02 = new CineMenuUser(id, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        int hashCode = cineMenuUser02.hashCode();
        boolean equals = cineMenuUser01.equals(cineMenuUser02);

        // Then
        assertEquals(hashCode, cineMenuUser02.hashCode());
        Assertions.assertTrue(equals);
    }

    @Test
    @DisplayName("Test CineMenuUser entity EqualsAndHashcode of id")
    void testCineMenuUserEntity03() {
        // Given
        String firstId = "1L";
        String secondId = "2L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();
        UserProfile userProfile = new UserProfile("bio", List.of(), Map.of());

        // When
        CineMenuUser cineMenuUser01 = new CineMenuUser(firstId, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        CineMenuUser cineMenuUser02 = new CineMenuUser(secondId, userProfile, name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        boolean equals = cineMenuUser01.equals(cineMenuUser02);

        // Then
        Assertions.assertFalse(equals);
    }

    @Test
    @DisplayName("Test CineMenuUser internal update methods")
    void testUpdate01() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();

        var userProfileRequest = new UserProfileRequestDto("new name", "new username", "new bio");
        CineMenuUser cineMenuUser = new CineMenuUser(id, new UserProfile("bio", List.of(), Map.of()),name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        // When
        cineMenuUser.updateProfile(userProfileRequest);

        // Then
        assertEquals("new name", cineMenuUser.getName());
        assertEquals("new username", cineMenuUser.getUsername());
        assertEquals("new bio", cineMenuUser.getProfile().getBiography());
    }

    @Test
    @DisplayName("Test CineMenuUser internal update methods")
    void testUpdateScene02() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();

        var userProfileRequest = new UserProfileRequestDto(null, null, null);
        CineMenuUser cineMenuUser = new CineMenuUser(id, new UserProfile("bio", List.of(), Map.of()),name, username, email, password, registrationDate, false, null, List.of(new MediaList()));

        // When
        cineMenuUser.updateProfile(userProfileRequest);

        // Then
        assertEquals("John Doe", cineMenuUser.getName());
        assertEquals("johndoe", cineMenuUser.getUsername());
        assertEquals("bio", cineMenuUser.getProfile().getBiography());
    }

    @Test
    @DisplayName("Test setPreferences method whit valid requestDto")
    void testSetPreferencesScene01() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();
        List<CineMenuGenres> genreList = new ArrayList<>();
        genreList.add(CineMenuGenres.ACTION);
        Map<Long, MediaType> mapReference = new HashMap<>();
        mapReference.put(12L, MediaType.MOVIE);
        UserProfile userProfile = new UserProfile("bio", genreList, mapReference);
        CineMenuUser cineMenuUser = new CineMenuUser(id, userProfile,name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        List<UserPreferencesRequestDto.CineMenuGenresId> genresIdList = new ArrayList<>();
        genresIdList.add(new UserPreferencesRequestDto.CineMenuGenresId(CineMenuGenres.ANIMATION.getCineMenuGenreId()));
        List<UserPreferencesRequestDto.UserTMDBMediaRequestReference> mapReferenceRequest = new ArrayList<>();
        mapReferenceRequest.add(new UserPreferencesRequestDto.UserTMDBMediaRequestReference(1396L, MediaType.TV));

        UserPreferencesRequestDto requestDto = new UserPreferencesRequestDto(genresIdList, mapReferenceRequest);

        // When
        cineMenuUser.setPreferences(requestDto);

        // Then
        assertEquals(2, cineMenuUser.getProfile().getGenrePreferences().size());
        assertEquals(2, cineMenuUser.getProfile().getTmdbMediaReferences().size());
    }

    @Test
    @DisplayName("Test setPreferences method whit existing requestDto")
    void testSetPreferencesScene02() {
        // Given
        String id = "1L";
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password";
        OffsetDateTime registrationDate = OffsetDateTime.now();
        List<CineMenuGenres> genreList = new ArrayList<>();
        genreList.add(CineMenuGenres.ACTION);
        Map<Long, MediaType> mapReference = new HashMap<>();
        mapReference.put(12L, MediaType.MOVIE);
        UserProfile userProfile = new UserProfile("bio", genreList, mapReference);
        CineMenuUser cineMenuUser = new CineMenuUser(id, userProfile,name, username, email, password, registrationDate, false, null, List.of(new MediaList()));
        List<UserPreferencesRequestDto.CineMenuGenresId> genresIdList = new ArrayList<>();
        genresIdList.add(new UserPreferencesRequestDto.CineMenuGenresId(CineMenuGenres.ACTION.getCineMenuGenreId()));
        List<UserPreferencesRequestDto.UserTMDBMediaRequestReference> mapReferenceRequest = new ArrayList<>();
        mapReferenceRequest.add(new UserPreferencesRequestDto.UserTMDBMediaRequestReference(12L, MediaType.MOVIE));

        UserPreferencesRequestDto requestDto = new UserPreferencesRequestDto(genresIdList, mapReferenceRequest);

        // When
        cineMenuUser.setPreferences(requestDto);

        // Then
        assertEquals(1, cineMenuUser.getProfile().getGenrePreferences().size());
        assertEquals(1, cineMenuUser.getProfile().getTmdbMediaReferences().size());
    }
}