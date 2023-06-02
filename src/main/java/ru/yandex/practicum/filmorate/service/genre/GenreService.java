package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreService {
    public Genre create(Genre genre);

    public Collection<Genre> readAll();

    public Genre update(Genre genre);

    public Genre getGenreById(int id);
}
