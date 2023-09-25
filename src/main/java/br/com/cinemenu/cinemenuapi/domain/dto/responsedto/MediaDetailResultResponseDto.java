package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;

import java.math.BigDecimal;
import java.util.List;

public record MediaDetailResultResponseDto(
        Long id,
        String posterPath,
        String backdropPath,
        String title,
        String originalTitle,
        String description,
        String releaseDate,
        MediaType mediaType,
        Double voteAverageTMDB,
        BigDecimal budged,
        BigDecimal revenue,
        List<ProductionCompanyPreview> productionCompanies,
        List<CineMenuGenres> genres,
        List<CastPreview> cast,
        List<DirectorPreview> directors,
        List<StreamProviderPreview> streamProviders,
        List<VideoPreview> videos
) {

    public record ProductionCompanyPreview(
            Long id,
            String logoPath,
            String name,
            String originalCountry
    ) {
    }

    public record CastPreview(
            Long id,
            String title,
            String posterPath
    ) {
    }

    public record DirectorPreview(
            Long id,
            String title,
            String posterPath
    ) {
    }

    public record StreamProviderPreview(
            Long id,
            String name,
            String logoPath
    ) {
    }
    public record VideoPreview(
            String key,
            String name,
            String site,
            Integer size,
            String type
    ){
    }

}
