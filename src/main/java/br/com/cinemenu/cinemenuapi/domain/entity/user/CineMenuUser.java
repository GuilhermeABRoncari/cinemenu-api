package br.com.cinemenu.cinemenuapi.domain.entity.user;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserPreferencesRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "CineMenuUser")
@Table(name = "cine_menu_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE cine_menu_user SET deleted = true, deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted = false")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CineMenuUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Embedded
    private UserProfile profile;
    private String name;
    private String username;
    private String email;
    private String password;
    @Column(name = "registration_date")
    private OffsetDateTime registrationDate;
    private Boolean deleted;
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MediaList> mediaLists = new ArrayList<>();

    public CineMenuUser(CineMenuUserRequestDto userDto, String encodedPassword) {
        this.name = userDto.name();
        this.username = userDto.username();
        this.profile = new UserProfile("", null, null);
        this.email = userDto.email();
        this.password = encodedPassword;
        this.registrationDate = OffsetDateTime.now();
        this.deleted = Boolean.FALSE;
        this.deletedAt = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public MediaList getMediaListById(String mediaListId) {
        return this.getMediaLists().stream().filter(mediaList -> mediaList.getId().equals(mediaListId)).findFirst().get();
    }

    public void updateProfile(UserProfileRequestDto dto) {
        if(dto.name() != null) this.name = dto.name();
        if(dto.username() != null) this.username = dto.username();
        this.profile.update(dto);
    }

    public void setPreferences(UserPreferencesRequestDto userPreferencesRequestDto) {
        userPreferencesRequestDto.genres().forEach(cineMenuGenres -> {
            if (!this.profile.getGenrePreferences().contains(CineMenuGenres.fromId(cineMenuGenres.id()))) {
                this.profile.getGenrePreferences().add(CineMenuGenres.fromId(cineMenuGenres.id()));
            }
        });

        userPreferencesRequestDto.medias().forEach(userTMDBMediaRequestReference -> {
            if (!this.profile.getTmdbMediaReferences().containsKey(userTMDBMediaRequestReference.tmdbId()) &&
                    !this.profile.getTmdbMediaReferences().containsValue(userTMDBMediaRequestReference.mediaType())) {
                this.profile.getTmdbMediaReferences().put(userTMDBMediaRequestReference.tmdbId(), userTMDBMediaRequestReference.mediaType());
            }
        });
    }
}
