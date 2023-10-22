package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record PreviewMovieDetailsResultDto(
        boolean adult,
        @JsonAlias("backdrop_path")
        String backdropPath,
        @JsonAlias("belongs_to_collection")
        BelongsToCollection belongsToCollection,
        long budget,
        List<Genre> genres,
        String homepage,
        Long id,
        @JsonAlias("imdb_id")
        String imdbId,
        @JsonAlias("original_language")
        String originalLanguage,
        @JsonAlias("original_title")
        String originalTitle,
        String overview,
        double popularity,
        @JsonAlias("poster_path")
        String posterPath,
        @JsonAlias("production_companies")
        List<ProductionCompany> productionCompanies,
        @JsonAlias("production_countries")
        List<ProductionCountry> productionCountries,
        @JsonAlias("release_date")
        String releaseDate,
        long revenue,
        int runtime,
        @JsonAlias("spoken_languages")
        List<SpokenLanguage> spokenLanguages,
        String status,
        String tagline,
        String title,
        boolean video,
        @JsonAlias("vote_average")
        double voteAverage,
        @JsonAlias("vote_count")
        int voteCount
) {
    public record BelongsToCollection(int id, String name, @JsonAlias("poster_path") String posterPath, @JsonAlias("backdrop_path") String backdropPath) {
    }

    public record Genre(int id, String name) {
    }

    public record ProductionCompany(int id, @JsonAlias("logo_path") String logoPath, String name, @JsonAlias("origin_country") String originCountry) {
    }

    public record ProductionCountry(String iso3166_1, String name) {
    }

    public record SpokenLanguage(@JsonAlias("english_name") String englishName, String iso639_1, String name) {
    }
}

