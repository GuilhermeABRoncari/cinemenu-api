package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ConfirmPasswordRecoveryResponseDto(
        @JsonProperty(index = 0)
        String message,
        @JsonProperty(index = 1)
        String email,
        @JsonProperty(value = "token", index = 2)
        String newAccessToken
) {
}
