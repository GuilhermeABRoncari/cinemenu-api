package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.*;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.InvalidSearchException;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import br.com.cinemenu.cinemenuapi.rest.repository.PreviewMediaRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class PreviewMediaService {

    PreviewMediaRepository previewMediaRepository;
    private static final String INVALID_SEARCH_MEDIA = "invalid media type: %s";

    public PreviewMediaResponsePage getSearchResponse(String search, Integer page) {
        val previewMediaResponse = previewMediaRepository.getSearchPreviewMediaResponse(search, page);

        List<CineMenuMediaResponse> multiSearchList = previewMediaResponse.results().stream()
                .filter(media -> !media.media_type().equals(MediaType.PERSON.name().toLowerCase()))
                .map(PreviewMediaMapper::genericMediaMap).toList();

        return new PreviewMediaResponsePage(
                previewMediaResponse.page(),
                multiSearchList,
                previewMediaResponse.total_pages());
    }

    public PreviewMediaResponsePage getGenreResponse(List<Integer> genreId, Integer page) {
        return previewMediaRepository.getGenrePreviewMediaResponse(genreId, page);
    }

    public PreviewMediaResponsePage getPopularPeopleList(Integer page) {
        var result = previewMediaRepository.getPeopleListResults(page);

        List<CineMenuMediaResponse> popularPeopleList = result.results().stream().map(PreviewMediaMapper::personMediaMap).toList();
        return new PreviewMediaResponsePage(result.page(), popularPeopleList, result.totalPages());
    }

    public PreviewMediaResponsePage getMovieListByActor(Long id) {
        PreviewActorCreditsListResults movieListByActorId = previewMediaRepository.getMovieListByActorId(id);
        movieListByActorId.results().sort(Comparator.comparing(PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse::popularity).reversed());

        var resultList = movieListByActorId.results().stream().map(PreviewMediaMapper::movieMediaMap).toList();

        return new PreviewMediaResponsePage(1, resultList, null);
    }

    public PreviewMediaResponsePage getSeriesListByActor(Long id) {
        PreviewActorCreditsListResults seriesListByActorId = previewMediaRepository.getSeriesListByActorId(id);
        seriesListByActorId.results().sort(Comparator.comparing(PreviewActorCreditsListResults.PreviewActorCreditsListResultsResponse::popularity).reversed());

        var resultList = seriesListByActorId.results().stream().map(PreviewMediaMapper::tvMediaMap).toList();

        return new PreviewMediaResponsePage(1, resultList, null);
    }

    public PreviewMediaResponsePage getSimilarByIdAndMedia(Long id, MediaType media, Integer page) {
        if (media.equals(MediaType.TV)) {
            PreviewMediaResults similarTVShowListById = previewMediaRepository.getSimilarTVShowListById(id, page);
            List<CineMenuMediaResponse> list = similarTVShowListById.results().stream().map(PreviewMediaMapper::tvMediaMap).toList();

            return new PreviewMediaResponsePage(page, list, similarTVShowListById.total_pages());
        }
        if (media.equals(MediaType.MOVIE)) {
            PreviewMediaResults similarMovieListById = previewMediaRepository.getSimilarMovieListById(id, page);
            List<CineMenuMediaResponse> list = similarMovieListById.results().stream().map(PreviewMediaMapper::movieMediaMap).toList();

            return new PreviewMediaResponsePage(page, list, similarMovieListById.total_pages());
        }

        throw new InvalidSearchException(INVALID_SEARCH_MEDIA.formatted(media));
    }

    public ResponseEntity<MediaDetailResultResponseDto> getMediaDetail(MediaType media, Long id) {
        return null;
    }
}
