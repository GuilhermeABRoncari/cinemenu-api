package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;

public record UserResponseDto(String id, String username) {
    public UserResponseDto(CineMenuUser cineMenuUser) {
        this(cineMenuUser.getId(), cineMenuUser.getUsername());
    }
}
