package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.AccountDeleteRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.LoginRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.TokenResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserProfileResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.CineMenuEntityNotFoundException;
import br.com.cinemenu.cinemenuapi.infra.security.SecurityConfigurations;
import br.com.cinemenu.cinemenuapi.infra.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CineMenuUserService {

    private final UserRepository repository;
    private final SecurityConfigurations securityConfigurations;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    private static final String EMAIL_IN_USE = "This email is already in use";
    private static final String INVALID_LOGIN = "Email or password is invalid";
    private static final String USERNAME_IN_USE = "This username is already in use";
    private static final String EMAIL_DISABLE = "This email is disable";
    private static final String INVALID_EMAIL = "The email address provided must be the same as the one registered by the account owner";
    private static final String USER_NOT_FOUND = "User not found by id: %s";

    @Transactional
    public TokenResponseDto sign(CineMenuUserRequestDto userDto) {

        if (repository.existsByEmail(userDto.email())) throw new IllegalArgumentException(EMAIL_IN_USE);
        if (repository.checkUserDisableEmail(userDto.email())) throw new IllegalArgumentException(EMAIL_DISABLE);
        if (repository.existsByUsername(userDto.username())) throw new IllegalArgumentException(USERNAME_IN_USE);
        if (repository.checkUserDisableUsername(userDto.username())) throw new IllegalArgumentException(USERNAME_IN_USE);

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

    public UserProfileResponseDto updateUserProfile(CineMenuUser user, UserProfileRequestDto dto) {
        if (repository.existsByUsername(dto.username())) throw new IllegalArgumentException(USERNAME_IN_USE);
        user.updateProfile(dto);
        return new UserProfileResponseDto(repository.save(user));
    }
}
