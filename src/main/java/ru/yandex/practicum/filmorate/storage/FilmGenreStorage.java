package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreStorage {
    void create(long filmId, long genreId);

    List<Genre> readGenresByFilmId(long filmId);

    void delete(long filmId);
}
