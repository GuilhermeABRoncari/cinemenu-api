package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}
