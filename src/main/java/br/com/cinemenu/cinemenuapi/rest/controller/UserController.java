package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.AccountDeleteRequestDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final AuthenticationFacade authenticationFacade;
    private static final String INVALID_EMAIL = "The email address provided must be the same as the one registered by the account owner";

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestBody @Valid AccountDeleteRequestDto dto) {
        String username = authenticationFacade.getAuthentication().getName();
        CineMenuUser user = (CineMenuUser) repository.findByUsername(username);

        if (user.getEmail().equals(dto.email())) {
            repository.delete(user);
        } else throw new IllegalArgumentException(INVALID_EMAIL);
        return ResponseEntity.noContent().build();
    }
}
