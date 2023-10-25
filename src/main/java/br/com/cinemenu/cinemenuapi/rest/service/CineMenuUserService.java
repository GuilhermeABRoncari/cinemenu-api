package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.*;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.TokenResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserPreferencesResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserProfileResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.CineMenuEntityNotFoundException;
import br.com.cinemenu.cinemenuapi.infra.security.SecurityConfigurations;
import br.com.cinemenu.cinemenuapi.infra.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class CineMenuUserService {

    private final UserRepository repository;
    private final SecurityConfigurations securityConfigurations;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    private final PreviewMediaService mediaService;

    private static final String EMAIL_IN_USE = "This email is already in use";
    private static final String INVALID_LOGIN = "Email or password is invalid";
    private static final String USERNAME_IN_USE = "This username is already in use";
    private static final String EMAIL_DISABLE = "This email is disable";
    private static final String INVALID_EMAIL = "The email address provided must be the same as the one registered by the account owner";
    private static final String USER_NOT_FOUND = "User not found by id: %s";
    private static final String EMPTY_PREFERENCES = "User: %s preferences is empty, try providing preferences on the endpoint: POST:user/preference";

    @Transactional
    public TokenResponseDto sign(CineMenuUserRequestDto userDto) {

        if (repository.existsByEmail(userDto.email())) throw new IllegalArgumentException(EMAIL_IN_USE);
        if (repository.checkUserDisableEmail(userDto.email())) throw new IllegalArgumentException(EMAIL_DISABLE);
        if (repository.existsByUsername(userDto.username())) throw new IllegalArgumentException(USERNAME_IN_USE);
        if (repository.checkUserDisableUsername(userDto.username()))
            throw new IllegalArgumentException(USERNAME_IN_USE);

        var user = new CineMenuUser(userDto, securityConfigurations.passwordEncoder().encode(userDto.password()));

        repository.save(user);

        return login(new LoginRequestDto(userDto.email(), userDto.password()));
    }

    public TokenResponseDto login(LoginRequestDto loginDto) {
        var user = repository.getReferenceByEmail(loginDto.email());
        if (user == null) throw new IllegalArgumentException(INVALID_LOGIN);
        var authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), loginDto.password());
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            if (!authenticate.isAuthenticated()) throw new IllegalArgumentException(INVALID_LOGIN);
            String tokenJWT = tokenService.generateToken((CineMenuUser) authenticate.getPrincipal());
            return new TokenResponseDto(tokenJWT);
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException(INVALID_LOGIN);
        }
    }

    public void deleteUserAccount(CineMenuUser user, AccountDeleteRequestDto dto) {
        if (user.getEmail().equals(dto.email())) {
            repository.delete(user);
        } else throw new IllegalArgumentException(INVALID_EMAIL);
    }

    public UserProfileResponseDto getUserProfile(CineMenuUser user) {
        return new UserProfileResponseDto(user);
    }

    public UserProfileResponseDto getUserProfileById(String id) {
        return new UserProfileResponseDto(
                repository.findById(id).orElseThrow(() -> new CineMenuEntityNotFoundException(USER_NOT_FOUND.formatted(id)))
        );
    }

    @Transactional
    public UserProfileResponseDto updateUserProfile(CineMenuUser user, UserProfileRequestDto dto) {
        if (repository.existsByUsername(dto.username())) throw new IllegalArgumentException(USERNAME_IN_USE);
        user.updateProfile(dto);
        return new UserProfileResponseDto(repository.save(user));
    }

    @Transactional
    @Generated
    public UserPreferencesResponseDto setUserPreferences(CineMenuUser user, UserPreferencesRequestDto userPreferencesRequestDto) {
        user.setPreferences(userPreferencesRequestDto);
        repository.save(user);
        return new UserPreferencesResponseDto(user);
    }

    @Generated
    public PreviewMediaResponsePage getRecommendations(CineMenuUser user, Integer pageNumber) {
        Integer byGender = 0;
        Integer byTMDBIdReference = 1;
        Random random = new Random();
        Integer randomInt = random.nextInt(2);

        if (randomInt.equals(byGender) && !user.getProfile().getGenrePreferences().isEmpty()) {
            return mediaService.getGenreResponse(user.getProfile().getGenrePreferences(), pageNumber);
        }

        if (randomInt.equals(byTMDBIdReference) && !user.getProfile().getTmdbMediaReferences().isEmpty()) {
            Map<Long, MediaType> tmdbMediaReferences = user.getProfile().getTmdbMediaReferences();
            ArrayList<Long> keys = new ArrayList<>(tmdbMediaReferences.keySet());

            Long randomId = keys.get(random.nextInt(keys.size()));

            return mediaService.getSimilarByIdAndMedia(randomId, tmdbMediaReferences.get(randomId), pageNumber);
        }

        throw new IllegalArgumentException(EMPTY_PREFERENCES.formatted(user.getUsername()));
    }
}
