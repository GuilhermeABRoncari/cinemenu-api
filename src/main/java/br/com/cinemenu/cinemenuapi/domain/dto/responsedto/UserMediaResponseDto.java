package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.entity.UserMedia;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserMediaResponseDto(
        @JsonProperty(index = 0)
        String id,
        @JsonProperty(value = "id_tmdb", index = 1)
        Integer idTmdb,
        @JsonProperty(value = "media_type", index = 2)
        MediaType mediaType,
        @JsonProperty(index = 3)
        String note,
        @JsonProperty(index = 4)
        Double userRating,
        @JsonProperty(index = 5)
        Boolean watched) {
        public UserMediaResponseDto(UserMedia userMedia) {
                this(userMedia.getId(), userMedia.getIdTmdb(), userMedia.getMediaType(), userMedia.getNote(), userMedia.getUserRating(), userMedia.getWatched());
        }
}
