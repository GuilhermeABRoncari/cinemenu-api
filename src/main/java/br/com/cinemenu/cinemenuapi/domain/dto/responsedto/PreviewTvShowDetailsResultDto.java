package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record PreviewTvShowDetailsResultDto(
        boolean adult,
        @JsonAlias("backdrop_path")
        String backdropPath,
        @JsonAlias("created_by")
        List<Object> createdBy,
        @JsonAlias("episode_run_time")
        List<Integer> episodeRunTime,
        @JsonAlias("first_air_date")
        String firstAirDate,
        List<Genre> genres,
        String homepage,
        Long id,
        @JsonAlias("in_production")
        boolean inProduction,
        List<String> languages,
        @JsonAlias("last_air_date")
        String lastAirDate,
        @JsonAlias("last_episode_to_air")
        Episode lastEpisodeToAir,
        String name,
        @JsonAlias("next_episode_to_air")
        Object nextEpisodeToAir,
        List<Network> networks,
        @JsonAlias("number_of_episodes")
        int numberOfEpisodes,
        @JsonAlias("number_of_seasons")
        int numberOfSeasons,
        @JsonAlias("origin_country")
        List<String> originCountry,
        @JsonAlias("original_language")
        String originalLanguage,
        @JsonAlias("original_name")
        String originalName,
        String overview,
        double popularity,
        @JsonAlias("poster_path")
        String posterPath,
        @JsonAlias("production_companies")
        List<ProductionCompany> productionCompanies,
        @JsonAlias("production_countries")
        List<Object> productionCountries,
        List<Season> seasons,
        @JsonAlias("spoken_languages")
        List<SpokenLanguage> spokenLanguages,
        String status,
        String tagline,
        String type,
        @JsonAlias("vote_average")
        double voteAverage,
        @JsonAlias("vote_count")
        int voteCount
) {
    public record Genre(int id, String name) {
    }

    public record Episode(
            int id,
            String name,
            String overview,
            @JsonAlias("vote_average")
            double voteAverage,
            @JsonAlias("vote_count")
            int voteCount,
            @JsonAlias("air_date")
            String airDate,
            @JsonAlias("episode_number")
            int episodeNumber,
            @JsonAlias("episode_type")
            String episodeType,
            @JsonAlias("production_code")
            String productionCode,
            Object runtime,
            @JsonAlias("season_number")
            int seasonNumber,
            @JsonAlias("show_id")
            int showId,
            @JsonAlias("still_path")
            Object stillPath
    ) {
    }

    public record Network(int id, @JsonAlias("logo_path") String logoPath, String name, @JsonAlias("origin_country") String originCountry) {
    }

    public record ProductionCompany(int id, @JsonAlias("logo_path") String logoPath, String name, @JsonAlias("origin_country") String originCountry) {
    }

    public record Season(
            @JsonAlias("air_date")
            String airDate,
            @JsonAlias("episode_count")
            int episodeCount,
            int id,
            String name,
            String overview,
            @JsonAlias("poster_path")
            Object posterPath,
            @JsonAlias("season_number")
            int seasonNumber,
            @JsonAlias("vote_average")
            int voteAverage
    ) {
    }

    public record SpokenLanguage(@JsonAlias("english_name") String englishName, String iso639_1, String name) {
    }
}
