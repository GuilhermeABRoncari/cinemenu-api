package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserMediaRequestDto(
        @Valid
        @NotNull
        List<UserMediaElementsDto> medias
) {
    public record UserMediaElementsDto(
            @JsonAlias("id_tmdb") @NotNull
            Integer idTmdb,
            @JsonAlias("media_type") @NotNull
            MediaType mediaType,
            String note,
            @JsonAlias("user_rating")
            Double userRating,
            Boolean watched
    ) {
    }
}
