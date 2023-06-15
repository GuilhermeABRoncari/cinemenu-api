package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreviewMediaResults(Integer page, List<PreviewMediaResultResponse> results, Integer total_pages) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PreviewMediaResultResponse(
            Boolean adult,
            String backdrop_path,
            Integer id,
            String title,
            String original_language,
            String original_title,
            String overview,
            String poster_path,
            String profile_path,
            String media_type,
            List<Integer> genre_ids,
            double popularity,
            String release_date,
            Boolean video,
            double vote_average,

            Integer vote_count,

            String name,
            String original_name,
            String first_air_date,
            List<String> origin_country
    ) {}
}
