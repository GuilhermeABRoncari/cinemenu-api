package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import lombok.Generated;

@Generated
public record UserProfileResponseDto(String name, String username, String biography) {
    public UserProfileResponseDto(CineMenuUser user) {
        this(user.getName(), user.getUsername(), user.getProfile().getBiography());
    }
}
