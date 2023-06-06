package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface LikesStorage {
    void create(long userId, long filmId);

    List<User> readLikesByFilmId(long filmId);

    List<Film> getPopularFilms(long count);

    void delete(long userId, long filmId);
}
