package br.com.cinemenu.cinemenuapi.domain.entity.user;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Column(length = 1000)
    private String biography;

    public void update(UserProfileRequestDto dto) {
        if (dto.biography() != null) this.biography = dto.biography();
    }
}
