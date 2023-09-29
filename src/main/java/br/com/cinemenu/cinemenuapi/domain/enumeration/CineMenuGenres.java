package br.com.cinemenu.cinemenuapi.domain.enumeration;

public enum CineMenuGenres {
    ACTION(67),
    ADVENTURE(89),
    ANIMATION(13),
    COMEDY(32),
    CRIME(70),
    DOCUMENTARY(72),
    DRAMA(76),
    FAMILY(46),
    FANTASY(98),
    HISTORY(57),
    HORROR(66),
    MUSIC(91),
    MYSTERY(16),
    ROMANCE(38),
    SCIENCE_FICTION(78);

    private final Integer cineMenuGenreId;

    CineMenuGenres(int cineMenuGenreId) {
        this.cineMenuGenreId = cineMenuGenreId;
    }

    public static CineMenuGenres fromId(int cineMenuGenreId) {
        for (CineMenuGenres genre : CineMenuGenres.values()) {
            if (genre.cineMenuGenreId == cineMenuGenreId) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid genre id: " + cineMenuGenreId);
    }

}
