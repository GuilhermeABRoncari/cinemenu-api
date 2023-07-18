package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
