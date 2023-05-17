package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    public void addLike(long filmId, long userId);

    public void deleteLike(long filmId, long userId);

    public Collection<Film> getMostPopularFilms(Integer count);

    public Film create(Film film);

    public Film update(Film film);

    public Collection<Film> getFilms();

    public Film getFilmById(long id);
}
