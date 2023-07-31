package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MediaListResponseDto(
        String id,
        String title,
        String description,
        @JsonProperty("total_elements")
        Integer totalElements,
        ListVisibility visibility,
        @JsonProperty("amount_like")
        Integer amountLike,
        @JsonProperty("amount_copy")
        Integer amountCopy,
        @JsonProperty("owner_id")
        String ownerId
) {
    public MediaListResponseDto(MediaList previewMediaList) {
        this(
                previewMediaList.getId(), previewMediaList.getTitle(), previewMediaList.getDescription(), 0,
                previewMediaList.getVisibility(), previewMediaList.getAmountLike(), previewMediaList.getAmountCopy(),
                previewMediaList.getUser().getId()
        );
    }
}
