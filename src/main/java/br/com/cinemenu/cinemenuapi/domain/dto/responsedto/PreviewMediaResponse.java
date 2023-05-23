package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreviewMediaResponse(int page, List<PreviewMediaResultResponse> results) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PreviewMediaResultResponse(
            boolean adult,
            String backdrop_path,
            int id,
            String title,
            String original_language,
            String original_title,
            String overview,
            String poster_path,
            String media_type,
            List<Integer> genre_ids,
            double popularity,
            String release_date,
            boolean video,
            double vote_average,

            @JsonProperty("voteCount")
            int vote_count,

            String name,
            String first_air_date,
            List<String> origin_country
    ) {}
}
