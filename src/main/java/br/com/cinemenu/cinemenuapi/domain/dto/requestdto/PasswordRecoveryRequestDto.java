package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRecoveryRequestDto(
        @Email
        @NotBlank
        String email,
        @Size(min = 8, max = 8)
        @JsonAlias("recovery_hash")
        String recoveryHash,
        @JsonAlias("new_password")
        String newPassword
) {
}
