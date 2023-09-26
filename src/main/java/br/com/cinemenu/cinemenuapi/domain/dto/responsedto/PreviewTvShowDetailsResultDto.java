package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import java.util.List;

public record PreviewTvShowDetailsResultDto(
        boolean adult,
        String backdropPath,
        List<Object> createdBy,
        List<Integer> episodeRunTime,
        String firstAirDate,
        List<Genre> genres,
        String homepage,
        Long id,
        boolean inProduction,
        List<String> languages,
        String lastAirDate,
        Episode lastEpisodeToAir,
        String name,
        Object nextEpisodeToAir,
        List<Network> networks,
        int numberOfEpisodes,
        int numberOfSeasons,
        List<String> originCountry,
        String originalLanguage,
        String originalName,
        String overview,
        double popularity,
        String posterPath,
        List<ProductionCompany> productionCompanies,
        List<Object> productionCountries,
        List<Season> seasons,
        List<SpokenLanguage> spokenLanguages,
        String status,
        String tagline,
        String type,
        double voteAverage,
        int voteCount
) {
    public record Genre(int id, String name) {
    }

    public record Episode(
            int id,
            String name,
            String overview,
            double voteAverage,
            int voteCount,
            String airDate,
            int episodeNumber,
            String episodeType,
            String productionCode,
            Object runtime,
            int seasonNumber,
            int showId,
            Object stillPath
    ) {
    }

    public record Network(int id, String logoPath, String name, String originCountry) {
    }

    public record ProductionCompany(int id, String logoPath, String name, String originCountry) {
    }

    public record Season(
            String airDate,
            int episodeCount,
            int id,
            String name,
            String overview,
            Object posterPath,
            int seasonNumber,
            int voteAverage
    ) {
    }

    public record SpokenLanguage(String englishName, String iso639_1, String name) {
    }
}
