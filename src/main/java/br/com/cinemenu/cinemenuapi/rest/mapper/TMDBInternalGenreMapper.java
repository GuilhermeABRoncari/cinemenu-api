package br.com.cinemenu.cinemenuapi.rest.mapper;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaDetailsResultResponseDto.Genres;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMovieDetailsResultDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewTvShowDetailsResultDto;
import br.com.cinemenu.cinemenuapi.domain.enumeration.CineMenuGenres;

import java.util.*;

public class TMDBInternalGenreMapper {

    private TMDBInternalGenreMapper() {
        throw new IllegalStateException("Utility class");
    }

    private static Map<CineMenuGenres, Integer> movieGenreTMDBIdMap() {
        EnumMap<CineMenuGenres, Integer> map = new EnumMap<>(CineMenuGenres.class);
        map.put(CineMenuGenres.ACTION, 28);
        map.put(CineMenuGenres.ADVENTURE, 12);
        map.put(CineMenuGenres.ANIMATION, 16);
        map.put(CineMenuGenres.COMEDY, 35);
        map.put(CineMenuGenres.CRIME, 80);
        map.put(CineMenuGenres.DOCUMENTARY, 99);
        map.put(CineMenuGenres.DRAMA, 18);
        map.put(CineMenuGenres.FAMILY, 10751);
        map.put(CineMenuGenres.FANTASY, 14);
        map.put(CineMenuGenres.HISTORY, 36);
        map.put(CineMenuGenres.HORROR, 27);
        map.put(CineMenuGenres.MUSIC, 10402);
        map.put(CineMenuGenres.MYSTERY, 9648);
        map.put(CineMenuGenres.ROMANCE, 10749);
        map.put(CineMenuGenres.SCIENCE_FICTION, 878);

        return map;
    }

    private static Map<CineMenuGenres, Integer> tvShowGenreTMDBIdMap() {
        EnumMap<CineMenuGenres, Integer> map = new EnumMap<>(CineMenuGenres.class);
        map.put(CineMenuGenres.ACTION, 10759);
        map.put(CineMenuGenres.ADVENTURE, 10759);
        map.put(CineMenuGenres.ANIMATION, 16);
        map.put(CineMenuGenres.COMEDY, 35);
        map.put(CineMenuGenres.CRIME, 80);
        map.put(CineMenuGenres.DOCUMENTARY, 99);
        map.put(CineMenuGenres.DRAMA, 18);
        map.put(CineMenuGenres.FAMILY, 10751);
        map.put(CineMenuGenres.FANTASY, 10765);
        map.put(CineMenuGenres.MYSTERY, 9648);
        map.put(CineMenuGenres.SCIENCE_FICTION, 10765);

        return map;
    }

    public static List<Integer> mapToTMDBMovieIds(List<CineMenuGenres> genre) {
        return mapForIds(genre, movieGenreTMDBIdMap());
    }

    public static List<Integer> mapToTMDBTvShowIds(List<CineMenuGenres> genre) {
        return mapForIds(genre, tvShowGenreTMDBIdMap());
    }

    private static List<Integer> mapForIds(List<CineMenuGenres> genres, Map<CineMenuGenres, Integer> cineMenuGenresIntegerMap) {
        Map<CineMenuGenres, Integer> genreIdMap = null;
        genreIdMap = cineMenuGenresIntegerMap;
        List<Integer> genreIds = new ArrayList<>();

        for (CineMenuGenres genre : genres) {
            Integer genreId = genreIdMap.get(genre);
            if (genreId != null) {
                genreIds.add(genreId);
            }
        }

        return genreIds;
    }

    public static List<Genres> tvTMDBIdsMapToCineMenuGenres(List<PreviewTvShowDetailsResultDto.Genre> genres) {
        List<CineMenuGenres> list = genres.stream().map(genre -> CineMenuGenres.fromString(genre.name())).toList();
        return list.stream().map(Genres::new).toList();
    }

    public static List<Genres> movieTMDBIdsMapToCineMenuGenres(List<PreviewMovieDetailsResultDto.Genre> genres) {
        List<CineMenuGenres> list = genres.stream().map(genre -> CineMenuGenres.fromString(genre.name())).toList();
        return list.stream().map(Genres::new).toList();
    }
}
