package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    public Film create(Film film);

    public Film update(Film film);

    public Collection<Film> getFilms();

    public Film getFilmById(long id);
}
