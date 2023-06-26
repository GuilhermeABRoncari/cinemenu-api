package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreviewPopularResults(Integer page, List<PreviewPopularResultsResponse> results, @JsonAlias("total_pages") Integer totalPages) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PreviewPopularResultsResponse(
            Boolean adult,
            Integer gender,
            Long id,
            @JsonAlias("known_for")
            List<PreviewMediaResults.PreviewMediaResultResponse> knownFor,
            @JsonAlias("known_For_Department")
            String knownForDepartment,
            String name,
            Double popularity,
            @JsonAlias("profile_path")
            String profilePath
    ) {}
}
