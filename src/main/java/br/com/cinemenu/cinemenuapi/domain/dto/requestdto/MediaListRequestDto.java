package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MediaListRequestDto(
        @NotBlank
        String title,
        String description,
        @NotNull
        ListVisibility visibility) {
}
