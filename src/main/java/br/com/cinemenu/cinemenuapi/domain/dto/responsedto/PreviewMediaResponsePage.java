package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record PreviewMediaResponsePage(
        Integer page,
        List<CineMenuMediaResponse> results,
        @JsonAlias("total_pages")
        Integer totalPages) {
}
