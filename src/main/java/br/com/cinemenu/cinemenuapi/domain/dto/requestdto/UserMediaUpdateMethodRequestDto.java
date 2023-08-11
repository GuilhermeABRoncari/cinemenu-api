package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record UserMediaUpdateMethodRequestDto(
        String note,
        @JsonAlias("user_rating")
        Double userRating,
        Boolean watched
) {
}
