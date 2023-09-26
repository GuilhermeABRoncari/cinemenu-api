package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import java.util.List;

public record PreviewMovieDetailsResultDto(
        boolean adult,
        String backdropPath,
        BelongsToCollection belongsToCollection,
        long budget,
        List<Genre> genres,
        String homepage,
        Long id,
        String imdbId,
        String originalLanguage,
        String originalTitle,
        String overview,
        double popularity,
        String posterPath,
        List<ProductionCompany> productionCompanies,
        List<ProductionCountry> productionCountries,
        String releaseDate,
        long revenue,
        int runtime,
        List<SpokenLanguage> spokenLanguages,
        String status,
        String tagline,
        String title,
        boolean video,
        double voteAverage,
        int voteCount
) {
    public record BelongsToCollection(int id, String name, String posterPath, String backdropPath) {
    }

    public record Genre(int id, String name) {
    }

    public record ProductionCompany(int id, String logoPath, String name, String originCountry) {
    }

    public record ProductionCountry(String iso3166_1, String name) {
    }

    public record SpokenLanguage(String englishName, String iso639_1, String name) {
    }
}

