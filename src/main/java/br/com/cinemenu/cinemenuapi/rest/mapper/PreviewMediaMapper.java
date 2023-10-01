package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.*;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaDetailsResultResponseDto.*;

public class PreviewMediaMapper {

    private static final String DIRECTOR = "Director";

    private PreviewMediaMapper() {
    }

    public static CineMenuMediaResponse genericMediaMap(PreviewMediaResults.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                Objects.isNull(response.title()) ? response.name() : response.title(),
                Objects.isNull(response.poster_path()) ? response.profile_path() : response.poster_path(),
                MediaType.fromString(response.media_type()),
                Objects.isNull(response.release_date()) ? response.first_air_date() : response.release_date(),
                response.vote_average()
        );
    }

    public static CineMenuMediaResponse movieMediaMap(PreviewMediaResults.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.title(),
                response.poster_path(),
                MediaType.MOVIE,
                response.release_date(),
                response.vote_average()
        );
    }

    public static CineMenuMediaResponse movieMediaMap(PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.title(),
                response.posterPath(),
                MediaType.MOVIE,
                response.releaseDate(),
                response.voteAverage()
        );
    }

    public static CineMenuMediaResponse tvMediaMap(PreviewMediaResults.PreviewMediaResultResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.name(),
                response.poster_path(),
                MediaType.TV,
                response.first_air_date(),
                response.vote_average()
        );
    }

    public static CineMenuMediaResponse tvMediaMap(PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.name(),
                response.posterPath(),
                MediaType.TV,
                response.firstAirDate(),
                response.voteAverage()
        );
    }

    public static CineMenuMediaResponse personMediaMap(PreviewPopularResults.PreviewPopularResultsResponse response) {
        return new CineMenuMediaResponse(
                response.id(),
                response.name(),
                response.profilePath(),
                MediaType.PERSON,
                null,
                null
        );
    }

    public static MediaDetailsResultResponseDto mediaDetails(
            PreviewTvShowDetailsResultDto tvShowDetails,
            PreviewTvShowCreditsResultDto tvShowCredits,
            PreviewTvShowWatchProvidersResultDto tvShowWatchProviders,
            PreviewTvShowVideoResultDto previewTvShowVideoResultDto) {
        return new MediaDetailsResultResponseDto(
                tvShowDetails.id(),
                tvShowDetails.posterPath(),
                tvShowDetails.backdropPath(),
                tvShowDetails.name(),
                tvShowDetails.originalName(),
                tvShowDetails.overview(),
                tvShowDetails.firstAirDate(),
                MediaType.TV,
                tvShowDetails.voteAverage(),
                null,
                null,
                getListOfTvProductionCompanies(tvShowDetails.productionCompanies()),
                TMDBInternalGenreMapper.tvTMDBIdsMapToCineMenuGenres(tvShowDetails.genres()),
                getListOfTvCast(tvShowCredits.cast()),
                getListOfTvDirectors(tvShowCredits.crew()),
                getListOfTvWatchProviders(tvShowWatchProviders),
                getListOfTvVideos(previewTvShowVideoResultDto)
        );
    }

    public static MediaDetailsResultResponseDto mediaDetails(
            PreviewMovieDetailsResultDto movieDetails,
            PreviewMovieCreditsResultDto movieCredits,
            PreviewMovieWatchProvidersResultDto movieWatchProviders,
            PreviewMovieVideoResultDto movieVideos) {
        return new MediaDetailsResultResponseDto(
                movieDetails.id(),
                movieDetails.posterPath(),
                movieDetails.backdropPath(),
                movieDetails.title(),
                movieDetails.originalTitle(),
                movieDetails.overview(),
                movieDetails.releaseDate(),
                MediaType.MOVIE,
                movieDetails.voteAverage(),
                new BigDecimal(movieDetails.budget()),
                new BigDecimal(movieDetails.revenue()),
                getListOfMovieProductionCompanies(movieDetails.productionCompanies()),
                TMDBInternalGenreMapper.movieTMDBIdsMapToCineMenuGenres(movieDetails.genres()),
                getListOfMovieCast(movieCredits.cast()),
                getListOfMovieDirectors(movieCredits.crew()),
                getListOfMovieWatchProviders(movieWatchProviders),
                getListOfMovieVideos(movieVideos)
        );
    }

    private static List<VideoPreview> getListOfMovieVideos(PreviewMovieVideoResultDto movieVideos) {
        return movieVideos.results().stream().map(VideoPreview::new).toList();
    }

    private static List<StreamProviderPreview> getListOfMovieWatchProviders(PreviewMovieWatchProvidersResultDto movieWatchProviders) {
        List<StreamProviderPreview> providersList = new ArrayList<>();
        List<StreamProviderPreview> countryProvidersList = new ArrayList<>();

        for (String country : movieWatchProviders.results().keySet()) {
            if (movieWatchProviders.results().get(country) != null && movieWatchProviders.results().get(country).flatrate() != null) {
                countryProvidersList = movieWatchProviders.results().get(country).flatrate().stream().map(StreamProviderPreview::new).toList();
            }
            for (StreamProviderPreview provider : countryProvidersList) {
                if (!providersList.contains(provider)) {
                    providersList.add(provider);
                }
            }
        }

        return providersList;
    }

    private static List<DirectorPreview> getListOfMovieDirectors(List<PreviewMovieCreditsResultDto.CrewMember> crew) {
        List<DirectorPreview> directorsList = new ArrayList<>();
        crew.stream().map(crewMember -> {
            if (crewMember.job().equals(DIRECTOR)) {
                directorsList.add(new DirectorPreview(crewMember));
            }
            return null;
        }).toList();

        return directorsList;
    }

    private static List<CastPreview> getListOfMovieCast(List<PreviewMovieCreditsResultDto.CastMember> cast) {
        return cast.stream().map(CastPreview::new).toList();
    }

    private static List<ProductionCompanyPreview> getListOfMovieProductionCompanies(List<PreviewMovieDetailsResultDto.ProductionCompany> movieProductionCompanies) {
        if (movieProductionCompanies == null) return Collections.emptyList();
        return movieProductionCompanies.stream().map(ProductionCompanyPreview::new).toList();
    }

    private static List<ProductionCompanyPreview> getListOfTvProductionCompanies(List<PreviewTvShowDetailsResultDto.ProductionCompany> productionCompanies) {
        if (productionCompanies == null) return Collections.emptyList();
        return productionCompanies.stream().map(ProductionCompanyPreview::new).toList();
    }

    private static List<CastPreview> getListOfTvCast(List<PreviewTvShowCreditsResultDto.CastMember> cast) {
        return cast.stream().map(CastPreview::new).toList();
    }

    private static List<VideoPreview> getListOfTvVideos(PreviewTvShowVideoResultDto previewTvShowVideoResultDto) {
        return previewTvShowVideoResultDto.results().stream().map(VideoPreview::new).toList();
    }


    private static List<StreamProviderPreview> getListOfTvWatchProviders(PreviewTvShowWatchProvidersResultDto tvShowWatchProviders) {
        List<StreamProviderPreview> providersList = new ArrayList<>();
        List<StreamProviderPreview> countryProvidersList = new ArrayList<>();

        for (String country : tvShowWatchProviders.results().keySet()) {
            if (tvShowWatchProviders.results().get(country) != null && tvShowWatchProviders.results().get(country).flatrate() != null) {
                countryProvidersList = tvShowWatchProviders.results().get(country).flatrate().stream().map(StreamProviderPreview::new).toList();
            }
            for (StreamProviderPreview provider : countryProvidersList) {
                if (!providersList.contains(provider)) {
                    providersList.add(provider);
                }
            }
        }

        return providersList;
    }

    private static List<DirectorPreview> getListOfTvDirectors(List<PreviewTvShowCreditsResultDto.CrewMember> crew) {
        List<DirectorPreview> directorsList = new ArrayList<>();
        crew.stream().map(crewMember -> {
            if (crewMember.job().equals(DIRECTOR)) {
                directorsList.add(new DirectorPreview(crewMember));
            }
            return null;
        }).toList();

        return directorsList;
    }
}
