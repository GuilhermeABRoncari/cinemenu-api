package br.com.cinemenu.cinemenuapi.domain.entity.user;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Column(length = 1000)
    private String biography;

    @ElementCollection
    @CollectionTable(name = "user_profile_genre_preferences")
    @Enumerated(EnumType.STRING)
    @Column(name = "genre_preference")
    private List<Integer> genrePreferences;

    @ElementCollection
    @CollectionTable(name = "user_profile_media_references")
    @MapKeyColumn(name = "media_id")
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private Map<Long, MediaType> tmdbMediaReferences;

    public void update(UserProfileRequestDto dto) {
        if (dto.biography() != null) this.biography = dto.biography();
    }
}
