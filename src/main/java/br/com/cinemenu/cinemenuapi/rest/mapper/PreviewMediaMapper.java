package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;

import java.util.Objects;

public class PreviewMediaMapper {
    private PreviewMediaMapper() {
    }

    public static CineMenuMediaResponse map(PreviewMediaResponse.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                Objects.isNull(response.title()) ? response.name() : response.title(),
                Objects.isNull(response.poster_path()) ? response.profile_path() : response.poster_path(),
                MediaType.fromString(response.media_type()),
                Objects.isNull(response.release_date()) ? response.first_air_date() : response.release_date(),
                response.vote_average()
        );
    }
}
