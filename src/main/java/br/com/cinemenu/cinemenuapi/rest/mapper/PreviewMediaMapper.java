package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResults;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewPopularResults;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;

import java.util.Objects;

public class PreviewMediaMapper {
    private PreviewMediaMapper() {
    }

    public static CineMenuMediaResponse genericMediaMap(PreviewMediaResults.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                Objects.isNull(response.title()) ? response.name() : response.title(),
                Objects.isNull(response.poster_path()) ? response.profile_path() : response.poster_path(),
                MediaType.fromString(response.media_type()),
                Objects.isNull(response.release_date()) ? response.first_air_date() : response.release_date(),
                response.vote_average()
        );
    }
    public static CineMenuMediaResponse movieMediaMap(PreviewMediaResults.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.title(),
                response.poster_path(),
                MediaType.MOVIE,
                response.release_date(),
                response.vote_average()
        );
    }

    public static CineMenuMediaResponse tvMediaMap(PreviewMediaResults.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.name(),
                response.poster_path(),
                MediaType.TV,
                response.first_air_date(),
                response.vote_average()
        );
    }

    public static CineMenuMediaResponse personMediaMap(PreviewPopularResults.PreviewPopularResultsResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.name(),
                response.profilePath(),
                MediaType.PERSON,
                null,
                null
        );
    }
}
