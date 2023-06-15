package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.CineMenuMediaResponse;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.rest.mapper.PreviewMediaMapper;
import br.com.cinemenu.cinemenuapi.rest.repository.PreviewMediaRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreviewMediaService {

    PreviewMediaRepository previewMediaRepository;

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
}
