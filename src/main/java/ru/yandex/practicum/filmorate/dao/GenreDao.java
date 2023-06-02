package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreDao {
    Genre create(Genre genre);

    Collection<Genre> readAll();

    Genre update(Genre genre);

    Genre getGenreById(long id);
}
