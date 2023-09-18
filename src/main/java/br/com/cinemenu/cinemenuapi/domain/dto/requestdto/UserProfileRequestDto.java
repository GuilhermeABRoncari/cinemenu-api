package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record UserProfileRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String username,
        @JsonAlias("bio")
        String biography
) {
}
