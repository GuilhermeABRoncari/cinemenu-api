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

    private static Map<Integer, CineMenuGenres> movieCineMenuGenreMap() {
        Map<Integer, CineMenuGenres> map = new HashMap<>();
        map.put(28, CineMenuGenres.ACTION);
        map.put(12, CineMenuGenres.ADVENTURE);
        map.put(16, CineMenuGenres.ANIMATION);
        map.put(35, CineMenuGenres.COMEDY);
        map.put(80, CineMenuGenres.CRIME);
        map.put(99, CineMenuGenres.DOCUMENTARY);
        map.put(18, CineMenuGenres.DRAMA);
        map.put(10751, CineMenuGenres.FAMILY);
        map.put(14, CineMenuGenres.FANTASY);
        map.put(36, CineMenuGenres.HISTORY);
        map.put(27, CineMenuGenres.HORROR);
        map.put(10402, CineMenuGenres.MUSIC);
        map.put(9648, CineMenuGenres.MYSTERY);
        map.put(10749, CineMenuGenres.ROMANCE);
        map.put(878, CineMenuGenres.SCIENCE_FICTION);

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

    private static Map<Integer, CineMenuGenres> tvShowCineMnuGenreMap() {
        Map<Integer, CineMenuGenres> map = new HashMap<>();
        map.put(10759, CineMenuGenres.ACTION);
        map.put(10759, CineMenuGenres.ADVENTURE);
        map.put(16, CineMenuGenres.ANIMATION);
        map.put(35, CineMenuGenres.COMEDY);
        map.put(80, CineMenuGenres.CRIME);
        map.put(99, CineMenuGenres.DOCUMENTARY);
        map.put(18, CineMenuGenres.DRAMA);
        map.put(10751, CineMenuGenres.FAMILY);
        map.put(10765, CineMenuGenres.FANTASY);
        map.put(9648, CineMenuGenres.MYSTERY);
        map.put(10765, CineMenuGenres.SCIENCE_FICTION);

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

    private static List<CineMenuGenres> mapTMDBTvShowIdsToCineMenuGenres(List<Integer> tmdbTvShowIdList){
        List<CineMenuGenres> resultList = new ArrayList<>();
        tmdbTvShowIdList.forEach(id -> resultList.add(tvShowCineMnuGenreMap().get(id)));
        return resultList;
    }

    private static List<CineMenuGenres> mapTMDBMovieIdsToCineMenuGenres(List<Integer> tmdbMovieIdList){
        List<CineMenuGenres> resultList = new ArrayList<>();
        tmdbMovieIdList.forEach(id -> resultList.add(movieCineMenuGenreMap().get(id)));
        return resultList;
    }

    public static List<Genres> tvTMDBIdsMapToCineMenuGenres(List<PreviewTvShowDetailsResultDto.Genre> genres) {
        List<Integer> tmdbTvShowGenreIdList = genres.stream().map(PreviewTvShowDetailsResultDto.Genre::id).toList();
        List<CineMenuGenres> cineMenuGenres = mapTMDBTvShowIdsToCineMenuGenres(tmdbTvShowGenreIdList);

        return cineMenuGenres.stream().map(Genres::new).toList();
    }

    public static List<Genres> movieTMDBIdsMapToCineMenuGenres(List<PreviewMovieDetailsResultDto.Genre> genres) {
        List<Integer> tmdbMovieIdList = genres.stream().map(PreviewMovieDetailsResultDto.Genre::id).toList();
        List<CineMenuGenres> cineMenuGenres = mapTMDBMovieIdsToCineMenuGenres(tmdbMovieIdList);

        return cineMenuGenres.stream().map(Genres::new).toList();
    }
}
