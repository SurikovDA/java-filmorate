package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Genre create(Genre genre);

    Collection<Genre> readAll();

    Genre update(Genre genre);

    Genre getGenreById(long id);
}
