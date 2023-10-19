package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Generated;

import java.util.List;

@Generated
public record UserPreferencesRequestDto(
        List<CineMenuGenresId> genres,
        List<UserTMDBMediaRequestReference> medias) {

    public record CineMenuGenresId(Integer id){}
    public record UserTMDBMediaRequestReference(
            @JsonAlias("tmdb_id")
            Long tmdbId,
            @JsonAlias("media_type")
            MediaType mediaType) {

    }
}
