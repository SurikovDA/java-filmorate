package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreDao {
    void create(long filmId, long genreId);

    List<Genre> readGenresByFilmId(long filmId);

    void delete(long filmId);
}
