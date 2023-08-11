package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record UserMediaRequestDto(
        List<UserMediaElementsDto> medias
) {
    public record UserMediaElementsDto(
            @JsonAlias("id_tmdb")
            Integer idTmdb,
            @JsonAlias("media_type")
            MediaType mediaType,
            String note,
            @JsonAlias("user_rating")
            Double userRating,
            Boolean watched
    ) {
    }
}
