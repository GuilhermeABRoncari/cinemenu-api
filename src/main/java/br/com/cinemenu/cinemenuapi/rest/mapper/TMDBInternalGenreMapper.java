package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;

import java.util.ArrayList;
import java.util.List;

public class TMDBInternalGenreMapper {

    public static List<Integer> map(List<CineMenuGenres> genre) {
        List<Integer> tmdbGenreIds = new ArrayList<>();

        genre.forEach(cineMenuGenres -> {
            if (cineMenuGenres.equals(CineMenuGenres.ACTION)) List.of(28).forEach(integer -> tmdbGenreIds.add(integer));
            if (cineMenuGenres.equals(CineMenuGenres.ADVENTURE)) List.of(18, 10759).forEach(integer -> tmdbGenreIds.add(integer));
            if (cineMenuGenres.equals(CineMenuGenres.ANIMATION)) tmdbGenreIds.add(16);
            if (cineMenuGenres.equals(CineMenuGenres.COMEDY)) tmdbGenreIds.add(35);
            if (cineMenuGenres.equals(CineMenuGenres.CRIME)) tmdbGenreIds.add(80);
            if (cineMenuGenres.equals(CineMenuGenres.DOCUMENTARY)) tmdbGenreIds.add(99);
            if (cineMenuGenres.equals(CineMenuGenres.DRAMA)) tmdbGenreIds.add(18);
            if (cineMenuGenres.equals(CineMenuGenres.FAMILY)) tmdbGenreIds.add(10751);
            if (cineMenuGenres.equals(CineMenuGenres.FANTASY)) tmdbGenreIds.add(14);
            if (cineMenuGenres.equals(CineMenuGenres.HISTORY)) List.of(36, 10768).forEach(integer -> tmdbGenreIds.add(integer));
            if (cineMenuGenres.equals(CineMenuGenres.HORROR)) tmdbGenreIds.add(27);
            if (cineMenuGenres.equals(CineMenuGenres.MUSIC)) tmdbGenreIds.add(10402);
            if (cineMenuGenres.equals(CineMenuGenres.MYSTERY)) tmdbGenreIds.add(9648);
            if (cineMenuGenres.equals(CineMenuGenres.ROMANCE)) tmdbGenreIds.add(10749);
            if (cineMenuGenres.equals(CineMenuGenres.SCIENCE_FICTION)) List.of(878, 10765).forEach(integer -> tmdbGenreIds.add(integer));
        });

        return tmdbGenreIds;
    }
}
