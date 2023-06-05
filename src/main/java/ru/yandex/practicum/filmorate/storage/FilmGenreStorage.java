package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface FilmGenreStorage {
    void create(long filmId, long genreId);

    List<Genre> readGenresByFilmId(long filmId);

    void delete(long filmId);

    List<Genre> setGenresFilm(long filmId, Set<Genre> genres);
}
