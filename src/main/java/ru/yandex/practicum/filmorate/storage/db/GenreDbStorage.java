package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@RequiredArgsConstructor
@Component
@Primary
public class GenreDbStorage implements GenreDao {
    private final GenreDao genreDao;

    @Override
    public Genre create(Genre genre) {
        return genreDao.create(genre);
    }

    @Override
    public Collection<Genre> readAll() {
        return genreDao.readAll();
    }

    @Override
    public Genre update(Genre genre) {
        return genreDao.update(genre);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.getGenreById(id);
    }
}
