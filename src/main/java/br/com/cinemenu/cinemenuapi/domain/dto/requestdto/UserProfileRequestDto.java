package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record UserProfileRequestDto(
        String name,
        String username,
        @JsonAlias("bio")
        String biography
) {
}
