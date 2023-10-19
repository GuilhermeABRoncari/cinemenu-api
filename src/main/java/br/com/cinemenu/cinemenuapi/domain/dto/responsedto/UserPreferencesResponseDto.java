package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record UserPreferencesResponseDto(
        @JsonProperty(index = 0, value = "user_id")
        String userId,
        @JsonProperty(index = 1)
        String username,
        @JsonProperty(index = 2, value = "genre_preferences")
        List<CineMenuGenres> genrePreferences,
        @JsonProperty(index = 3, value = "tmdb_id_references")
        Map<Long, MediaType> tmdbReferences
) {
    public UserPreferencesResponseDto(CineMenuUser user) {
        this(user.getId(), user.getUsername(), user.getProfile().getGenrePreferences(), user.getProfile().getTmdbMediaReferences());
    }
}
