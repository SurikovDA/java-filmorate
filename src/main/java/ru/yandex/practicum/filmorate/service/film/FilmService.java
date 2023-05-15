package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    public void addLike(long filmId, long userId);

    public void deleteLike(long filmId, long userId);

    public Collection<Film> getMostPopularFilms(Integer count);
}
