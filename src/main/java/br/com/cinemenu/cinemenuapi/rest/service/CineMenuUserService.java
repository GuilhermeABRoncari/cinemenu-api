package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.LoginRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.TokenResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
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

    private UserRepository repository;
    private SecurityConfigurations securityConfigurations;
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    private static final String EMAIL_IN_USE = "This email is already in use";
    private static final String INVALID_LOGIN = "Email or password is invalid";
    private static final String USERNAME_IN_USE = "This username is already in use";

    @Transactional
    public TokenResponseDto sign(CineMenuUserRequestDto userDto) {
        if (repository.existsByEmail(userDto.email())) throw new IllegalArgumentException(EMAIL_IN_USE);
        if (repository.existsByUsername(userDto.username())) throw new IllegalArgumentException(USERNAME_IN_USE);

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
}
