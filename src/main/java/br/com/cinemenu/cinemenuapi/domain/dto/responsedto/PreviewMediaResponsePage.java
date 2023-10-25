package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;

import java.util.List;

@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreviewMediaResponsePage(
        Integer page,
        List<CineMenuMediaResponse> results,
        @JsonAlias("total_pages")
        Integer totalPages) {
}
