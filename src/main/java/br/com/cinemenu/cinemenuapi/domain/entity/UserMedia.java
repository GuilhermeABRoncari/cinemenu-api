package br.com.cinemenu.cinemenuapi.domain.entity;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaUpdateMethodRequestDto;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "UserMedia")
@Table(name = "user_media")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "id_tmdb")
    private Integer idTmdb;
    @Column(name = "media_type")
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    private String note;
    @Column(name = "user_rating")
    private Double userRating;
    private Boolean watched;

    public UserMedia(UserMediaRequestDto.UserMediaElementsDto requestDto) {
        this.idTmdb = requestDto.idTmdb();
        this.mediaType = requestDto.mediaType();
        this.note = requestDto.note();
        this.userRating = requestDto.userRating();
        this.watched = requestDto.watched();
    }

    public UserMedia(UserMedia userMedia) {
        this.idTmdb = userMedia.getIdTmdb();
        this.mediaType = userMedia.getMediaType();
        this.note = "";
        this.userRating = 0.0;
        this.watched = false;
    }

    public void update(UserMediaUpdateMethodRequestDto updateMethodRequestDto) {
        if (updateMethodRequestDto.note() != null) this.note = updateMethodRequestDto.note();
        if (updateMethodRequestDto.userRating() != null) this.userRating = updateMethodRequestDto.userRating();
        if (updateMethodRequestDto.watched() != null) this.watched = updateMethodRequestDto.watched();
    }
}
