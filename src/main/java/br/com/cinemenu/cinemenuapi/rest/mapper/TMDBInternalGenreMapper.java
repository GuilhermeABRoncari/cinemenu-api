package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;

import java.util.ArrayList;
import java.util.List;

public class TMDBInternalGenreMapper {
    private static List<Integer> GenreIds = new ArrayList<>();
    private static Integer action = 28;
    private static Integer adventure = 12;
    private static Integer actionAndAdventure = 10759;
    private static Integer animation = 16;
    private static Integer comedy = 35;
    private static Integer crime = 80;
    private static Integer documentary = 99;
    private static Integer drama = 18;
    private static Integer family = 10751;
    private static Integer fantasy = 14;
    private static Integer history = 36;
    private static Integer historyShow = 10768;
    private static Integer horror = 27;
    private static Integer music = 10402;
    private static Integer mystery = 9648;
    private static Integer romance = 10749;
    private static Integer scienceFiction = 878;
    private static Integer scienceFictionShow = 10765;

    public static List<Integer> mapToMovieIds(List<CineMenuGenres> genre) {
        GenreIds.clear();

        genre.forEach(cineMenuGenres -> {
            if (cineMenuGenres.equals(CineMenuGenres.ACTION)) GenreIds.add(action);
            if (cineMenuGenres.equals(CineMenuGenres.ADVENTURE)) GenreIds.add(adventure);
            if (cineMenuGenres.equals(CineMenuGenres.ANIMATION)) GenreIds.add(animation);
            if (cineMenuGenres.equals(CineMenuGenres.COMEDY)) GenreIds.add(comedy);
            if (cineMenuGenres.equals(CineMenuGenres.CRIME)) GenreIds.add(crime);
            if (cineMenuGenres.equals(CineMenuGenres.DOCUMENTARY)) GenreIds.add(documentary);
            if (cineMenuGenres.equals(CineMenuGenres.DRAMA)) GenreIds.add(drama);
            if (cineMenuGenres.equals(CineMenuGenres.FAMILY)) GenreIds.add(family);
            if (cineMenuGenres.equals(CineMenuGenres.FANTASY)) GenreIds.add(fantasy);
            if (cineMenuGenres.equals(CineMenuGenres.HISTORY)) GenreIds.add(history);
            if (cineMenuGenres.equals(CineMenuGenres.HORROR)) GenreIds.add(horror);
            if (cineMenuGenres.equals(CineMenuGenres.MUSIC)) GenreIds.add(music);
            if (cineMenuGenres.equals(CineMenuGenres.MYSTERY)) GenreIds.add(mystery);
            if (cineMenuGenres.equals(CineMenuGenres.ROMANCE)) GenreIds.add(romance);
            if (cineMenuGenres.equals(CineMenuGenres.SCIENCE_FICTION)) GenreIds.add(scienceFiction);
        });

        return GenreIds;
    }
    public static List<Integer> mapToTvShowIds(List<CineMenuGenres> genre) {
        GenreIds.clear();

        genre.forEach(cineMenuGenres -> {
            if (cineMenuGenres.equals(CineMenuGenres.ACTION) || cineMenuGenres.equals(CineMenuGenres.ADVENTURE)) GenreIds.add(actionAndAdventure);
            if (cineMenuGenres.equals(CineMenuGenres.ANIMATION)) GenreIds.add(animation);
            if (cineMenuGenres.equals(CineMenuGenres.COMEDY)) GenreIds.add(comedy);
            if (cineMenuGenres.equals(CineMenuGenres.CRIME)) GenreIds.add(crime);
            if (cineMenuGenres.equals(CineMenuGenres.DOCUMENTARY)) GenreIds.add(documentary);
            if (cineMenuGenres.equals(CineMenuGenres.DRAMA)) GenreIds.add(drama);
            if (cineMenuGenres.equals(CineMenuGenres.FAMILY)) GenreIds.add(family);
            if (cineMenuGenres.equals(CineMenuGenres.FANTASY)) GenreIds.add(fantasy);
            if (cineMenuGenres.equals(CineMenuGenres.HISTORY)) GenreIds.add(historyShow);
            if (cineMenuGenres.equals(CineMenuGenres.HORROR)) GenreIds.add(horror);
            if (cineMenuGenres.equals(CineMenuGenres.MUSIC)) GenreIds.add(music);
            if (cineMenuGenres.equals(CineMenuGenres.MYSTERY)) GenreIds.add(mystery);
            if (cineMenuGenres.equals(CineMenuGenres.ROMANCE)) GenreIds.add(romance);
            if (cineMenuGenres.equals(CineMenuGenres.SCIENCE_FICTION)) GenreIds.add(scienceFictionShow);
        });

        return GenreIds;
    }
}
