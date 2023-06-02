package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmDao {
    Film create(Film film);

    Collection<Film> getFilms();

    Film update(Film film);

    Film getFilmById(long id);
}
