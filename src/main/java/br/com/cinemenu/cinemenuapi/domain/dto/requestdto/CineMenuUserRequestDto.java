package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CineMenuUserRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String username,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        @JsonAlias("confirmation_password")
        String confirmationPassword
) {
}
