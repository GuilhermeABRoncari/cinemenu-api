package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreviewActorCreditsListResults(@JsonAlias("cast") List<PreviewActorCreditsListResultsResponse> results, Long id) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PreviewActorCreditsListResultsResponse (
            Boolean adult,
            @JsonAlias("backdrop_path")
            String backdropPath,
            @JsonAlias("genre_ids")
            List<Integer> genreIds,
            Long id,
            @JsonAlias("original_name")
            String originalName,
            @JsonAlias("original_title")
            String originalTitle,
            String name,
            String title,
            @JsonAlias("original_language")
            String originalLanguage,
            String overview,
            Double popularity,
            @JsonAlias("poster_path")
            String posterPath,
            @JsonAlias("release_date")
            String releaseDate,
            @JsonAlias("first_air_date")
            String firstAirDate,
            Boolean video,
            @JsonAlias("vote_average")
            Double voteAverage,
            @JsonAlias("vote_count")
            Integer voteCount,
            String character,
            @JsonAlias("credit_id")
            String creditId,
            @JsonAlias("episode_count")
            Integer episodeCount,
            Integer order
    ) {}
}
