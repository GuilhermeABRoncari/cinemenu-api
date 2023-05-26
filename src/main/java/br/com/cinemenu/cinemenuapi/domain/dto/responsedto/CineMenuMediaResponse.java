package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;

public record CineMenuMediaResponse(
        int id,
        String title,
        String poster_path,
        MediaType media_type,
        String release_date,
        double vote_average
) {}
