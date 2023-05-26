package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import jakarta.validation.constraints.NotBlank;

public record MediaSearchDTO(
        @NotBlank(message = "Error: search field is empty.")
        String search
) {
}
