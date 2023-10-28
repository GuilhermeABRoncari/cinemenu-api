package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaListResponseDto(
        @JsonProperty(index = 0)
        String id,
        @JsonProperty(index = 1)
        String title,
        @JsonProperty(index = 2)
        String description,
        @JsonProperty(value = "total_elements", index = 4)
        Integer totalElements,
        @JsonProperty(index = 3)
        ListVisibility visibility,
        @JsonProperty(value = "amount_like", index = 5)
        Integer amountLike,
        @JsonProperty(value = "amount_copy", index = 6)
        Integer amountCopy,
        @JsonProperty(value = "owner_id", index = 7)
        String ownerId,
        @JsonProperty(value = "created_by", index = 8)
        String createdBy,
        @JsonProperty(value = "last_change", index = 9)
        OffsetDateTime lastChange
) {
    public MediaListResponseDto(MediaList previewMediaList) {
        this(
                previewMediaList.getId(), previewMediaList.getTitle(), previewMediaList.getDescription(), previewMediaList.getUserMedias().size(),
                previewMediaList.getVisibility(), previewMediaList.getAmountLike(), previewMediaList.getAmountCopy(),
                previewMediaList.getUser().getId(), previewMediaList.getCreatedBy(), previewMediaList.getLastChange()
        );
    }
}
