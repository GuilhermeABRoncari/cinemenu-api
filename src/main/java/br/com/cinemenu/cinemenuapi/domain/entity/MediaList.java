package br.com.cinemenu.cinemenuapi.domain.entity;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "MediaList")
@Table(name = "user_media_list")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class MediaList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<UserMedia> userMedias = new ArrayList<>();
    private ListVisibility visibility;
    private Integer amountLike;
    private Integer amountCopy;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "last_change")
    private OffsetDateTime lastChange;
    @ManyToOne
    private CineMenuUser user;

    public MediaList(MediaListRequestDto requestDto, CineMenuUser user) {
        this.title = requestDto.title().trim();
        this.description = requestDto.description();
        this.visibility = requestDto.visibility();
        this.amountLike = 0;
        this.amountCopy = 0;
        this.createdAt = OffsetDateTime.now();
        this.user = user;
    }

    public void update(MediaListRequestDto requestDto) {
        if (requestDto.title() != null) this.title = requestDto.title();
        this.description = requestDto.description();
        if (requestDto.visibility() != null) this.visibility = requestDto.visibility();
        this.lastChange = OffsetDateTime.now();
    }
}
