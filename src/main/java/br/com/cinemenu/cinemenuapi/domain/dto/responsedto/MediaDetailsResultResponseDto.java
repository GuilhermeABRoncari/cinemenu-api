package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record MediaDetailsResultResponseDto(

        @JsonProperty(index = 0)
        Long id,
        @JsonProperty(value = "poster_path", index = 1)
        String posterPath,
        @JsonProperty(value = "backdrop_path", index = 2)
        String backdropPath,
        @JsonProperty(index = 3)
        String title,
        @JsonProperty(value = "original_title", index = 4)
        String originalTitle,
        @JsonProperty(index = 5)
        String description,
        @JsonProperty(value = "release_date", index = 6)
        String releaseDate,
        @JsonProperty(value = "media_type", index = 7)
        MediaType mediaType,
        @JsonProperty(value = "vote_average_tmdb", index = 8)
        Double voteAverageTMDB,
        @JsonProperty(index = 9)
        BigDecimal budget,
        @JsonProperty(index = 10)
        BigDecimal revenue,
        @JsonProperty(value = "production_companies", index = 11)
        List<ProductionCompanyPreview> productionCompanies,
        @JsonProperty(index = 12)
        List<Genres> genres,
        @JsonProperty(index = 13)
        List<CastPreview> cast,
        @JsonProperty(index = 14)
        List<DirectorPreview> directors,
        @JsonProperty(value = "stream_providers", index = 15)
        List<StreamProviderPreview> streamProviders,
        @JsonProperty(index = 16)
        List<VideoPreview> videos
) {

    public record ProductionCompanyPreview(

            @JsonProperty(index = 0)
            Long id,
            @JsonProperty(value = "logo_path", index = 1)
            String logoPath,
            @JsonProperty(index = 2)
            String name,
            @JsonProperty(value = "original_country", index = 3)
            String originalCountry
    ) {
        public ProductionCompanyPreview(PreviewTvShowDetailsResultDto.ProductionCompany productionCompany) {
            this((long) productionCompany.id(), productionCompany.logoPath(), productionCompany.name(), productionCompany.originCountry());
        }

        public ProductionCompanyPreview(PreviewMovieDetailsResultDto.ProductionCompany productionCompany) {
            this((long) productionCompany.id(), productionCompany.logoPath(), productionCompany.name(), productionCompany.originCountry());
        }
    }

    public record Genres(

            Long id,
            String name
    ) {
        public Genres(CineMenuGenres genre) {
            this(Long.valueOf(genre.cineMenuGenreId()), genre.name());
        }
    }

    public record CastPreview(
            @JsonProperty(index = 0)
            Long id,
            @JsonProperty(index = 1)
            String title,
            @JsonProperty(value = "poster_path", index = 2)
            String posterPath
    ) {
        public CastPreview(PreviewTvShowCreditsResultDto.CastMember castMember) {
            this((long) castMember.id(), castMember.name(), castMember.profile_path());
        }

        public CastPreview(PreviewMovieCreditsResultDto.CastMember castMember) {
            this((long) castMember.id(), castMember.name(), castMember.profile_path());
        }
    }

    public record DirectorPreview(

            @JsonProperty(index = 0)
            Long id,
            @JsonProperty(index = 1)
            String title,
            @JsonProperty(value = "poster_path", index = 2)
            String posterPath
    ) {
        public DirectorPreview(PreviewTvShowCreditsResultDto.CrewMember crewMember) {
            this(crewMember.id(), crewMember.name(), crewMember.profile_path());
        }

        public DirectorPreview(PreviewMovieCreditsResultDto.CrewMember crewMember) {
            this((long) crewMember.id(), crewMember.name(), crewMember.profile_path());
        }
    }

    public record StreamProviderPreview(

            @JsonProperty(index = 0)
            Long id,
            @JsonProperty(index = 1)
            String name,
            @JsonProperty(value = "logo_path", index = 2)
            String logoPath
    ) {
        public StreamProviderPreview(PreviewTvShowWatchProvidersResultDto.Flatrate provider) {
            this((long) provider.providerId(), provider.providerName(), provider.logoPath());
        }

        public StreamProviderPreview(PreviewMovieWatchProvidersResultDto.Flatrate provider) {
            this((long) provider.providerId(), provider.providerName(), provider.logoPath());
        }
    }

    public record VideoPreview(
            String key,
            String name,
            String site,
            Integer size,
            String type
    ) {
        public VideoPreview(PreviewTvShowVideoResultDto.VideoResult videoResult) {
            this(videoResult.key(), videoResult.name(), videoResult.site(), videoResult.size(), videoResult.type());
        }

        public VideoPreview(PreviewMovieVideoResultDto.VideoResult videoResult) {
            this(videoResult.key(), videoResult.name(), videoResult.site(), videoResult.size(), videoResult.type());
        }
    }

}
