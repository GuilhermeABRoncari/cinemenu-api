package br.com.cinemenu.cinemenuapi.domain.dto.responsedto;

public record CineMenuMediaResponse(
        int id,
        String title,
        String poster_path,
        String media_type,
        String release_date,
        double vote_average
) {}
