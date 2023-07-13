package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.SecurityConfigurations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CineMenuUserService {

    private UserRepository repository;
    private SecurityConfigurations securityConfigurations;

    public UserResponseDto sign(CineMenuUserRequestDto userDto) {
        if (repository.existsByEmail(userDto.email())) throw new IllegalArgumentException("This email is already in use.");
        if (repository.existsByUsername(userDto.username())) throw new IllegalArgumentException("This username is already in use.");

        var user = new CineMenuUser(userDto, securityConfigurations.passwordEncoder().encode(userDto.password()));
        repository.save(user);
        return new UserResponseDto(user);
    }

}
