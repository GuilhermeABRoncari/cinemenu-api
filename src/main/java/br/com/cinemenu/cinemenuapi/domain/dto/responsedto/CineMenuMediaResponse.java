package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CineMenuMediaResponse(
        Long id,
        String title,
        String poster_path,
        MediaType media_type,
        String release_date,
        Double vote_average
) {}
