package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponse;

import java.util.Objects;

public class PreviewMediaMapper {
    private PreviewMediaMapper() {
    }

    public static CineMenuMediaResponse map(PreviewMediaResponse.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                Objects.isNull(response.title()) ? response.name() : response.title(),
                response.poster_path(),
                response.media_type(),
                response.release_date(),
                response.vote_average()
        );
    }
}
