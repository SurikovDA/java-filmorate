package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@RequiredArgsConstructor
@Component
@Primary
public class FilmGenreDbStorage implements FilmGenreDao {
    private final FilmGenreDao filmGenreDao;

    @Override
    public void create(long filmId, long genreId) {
        filmGenreDao.create(filmId, genreId);
    }

    @Override
    public List<Genre> readGenresByFilmId(long filmId) {
        return filmGenreDao.readGenresByFilmId(filmId);
    }

    @Override
    public void delete(long filmId) {
        filmGenreDao.delete(filmId);
    }
}
