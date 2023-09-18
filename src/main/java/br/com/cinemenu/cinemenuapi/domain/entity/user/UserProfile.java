package br.com.cinemenu.cinemenuapi.domain.entity.user;

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

}
