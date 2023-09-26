package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;
import java.util.List;

public record MediaDetailsResultResponseDto(
        Long id,
        @JsonAlias("poster_path")
        String posterPath,
        @JsonAlias("backdrop_path")
        String backdropPath,
        String title,
        @JsonAlias("original_title")
        String originalTitle,
        String description,
        @JsonAlias("release_date")
        String releaseDate,
        @JsonAlias("media_type")
        MediaType mediaType,
        @JsonAlias("vote_average_tmdb")
        Double voteAverageTMDB,
        BigDecimal budget,
        BigDecimal revenue,
        @JsonAlias("production_companies")
        List<ProductionCompanyPreview> productionCompanies,
        List<Genres> genres,
        List<CastPreview> cast,
        List<DirectorPreview> directors,
        @JsonAlias("stream_providers")
        List<StreamProviderPreview> streamProviders,
        List<VideoPreview> videos
) {

    public record ProductionCompanyPreview(
            Long id,
            String logoPath,
            String name,
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
    ){
        public Genres(CineMenuGenres genre) {
            this(Long.valueOf(genre.cineMenuGenreId()), genre.name());
        }
    }

    public record CastPreview(
            Long id,
            String title,
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
            Long id,
            String title,
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
            Long id,
            String name,
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
    ){
        public VideoPreview(PreviewTvShowVideoResultDto.VideoResult videoResult) {
            this(videoResult.key(), videoResult.name(), videoResult.site(), videoResult.size(), videoResult.type());
        }

        public VideoPreview(PreviewMovieVideoResultDto.VideoResult videoResult) {
            this(videoResult.key(), videoResult.name(), videoResult.site(), videoResult.size(), videoResult.type());
        }
    }

}
