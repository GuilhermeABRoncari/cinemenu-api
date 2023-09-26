package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.*;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaDetailsResultResponseDto.DirectorPreview;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PreviewMediaMapper {

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
        return  new MediaDetailsResultResponseDto(
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

    private static List<MediaDetailsResultResponseDto.VideoPreview> getListOfMovieVideos(PreviewMovieVideoResultDto movieVideos) {
        return movieVideos.results().stream().map(MediaDetailsResultResponseDto.VideoPreview::new).toList();
    }

    private static List<MediaDetailsResultResponseDto.StreamProviderPreview> getListOfMovieWatchProviders(PreviewMovieWatchProvidersResultDto movieWatchProviders) {
        return movieWatchProviders.results().get("BR").flatrate().stream().map(MediaDetailsResultResponseDto.StreamProviderPreview::new).toList();
    }

    private static List<DirectorPreview> getListOfMovieDirectors(List<PreviewMovieCreditsResultDto.CrewMember> crew) {
        List<DirectorPreview> directorsList = new ArrayList<>();
        crew.stream().map(crewMember -> {
            if (crewMember.job().equals("Director")) {
                directorsList.add(new DirectorPreview(crewMember));
            }
            return null;
        }).toList();

        return directorsList;
    }

    private static List<MediaDetailsResultResponseDto.CastPreview> getListOfMovieCast(List<PreviewMovieCreditsResultDto.CastMember> cast) {
        return cast.stream().map(MediaDetailsResultResponseDto.CastPreview::new).toList();
    }

    private static List<MediaDetailsResultResponseDto.ProductionCompanyPreview> getListOfMovieProductionCompanies(List<PreviewMovieDetailsResultDto.ProductionCompany> movieProductionCompanies) {
        return movieProductionCompanies.stream().map(MediaDetailsResultResponseDto.ProductionCompanyPreview::new).toList();
    }

    private static List<MediaDetailsResultResponseDto.ProductionCompanyPreview> getListOfTvProductionCompanies(List<PreviewTvShowDetailsResultDto.ProductionCompany> productionCompanies) {
        return productionCompanies.stream().map(MediaDetailsResultResponseDto.ProductionCompanyPreview::new).toList();
    }

    private static List<MediaDetailsResultResponseDto.CastPreview> getListOfTvCast(List<PreviewTvShowCreditsResultDto.CastMember> cast) {
        return cast.stream().map(MediaDetailsResultResponseDto.CastPreview::new).toList();
    }

    private static List<MediaDetailsResultResponseDto.VideoPreview> getListOfTvVideos(PreviewTvShowVideoResultDto previewTvShowVideoResultDto) {
        return previewTvShowVideoResultDto.results().stream().map(MediaDetailsResultResponseDto.VideoPreview::new).toList();
    }


    private static List<MediaDetailsResultResponseDto.StreamProviderPreview> getListOfTvWatchProviders(PreviewTvShowWatchProvidersResultDto tvShowWatchProviders) {
        return tvShowWatchProviders.results().get("BR").flatrate().stream().map(MediaDetailsResultResponseDto.StreamProviderPreview::new).toList();
    }

    private static List<DirectorPreview> getListOfTvDirectors(List<PreviewTvShowCreditsResultDto.CrewMember> crew) {
        List<DirectorPreview> directorsList = new ArrayList<>();
        crew.stream().map(crewMember -> {
            if (crewMember.job().equals("Director")) {
                directorsList.add(new DirectorPreview(crewMember));
            }
            return null;
        }).toList();

        return directorsList;
    }
}
