package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import java.util.List;

public record PreviewMovieVideoResultDto(int id, List<VideoResult> results) {

    public record VideoResult(
            String iso_639_1,
            String iso_3166_1,
            String name,
            String key,
            String site,
            int size,
            String type,
            boolean official,
            String published_at,
            String id
    ) {
    }
}
