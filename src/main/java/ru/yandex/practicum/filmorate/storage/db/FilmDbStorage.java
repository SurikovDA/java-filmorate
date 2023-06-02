package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@RequiredArgsConstructor
@Component
@Primary
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final FilmDao filmDao;
    @Override
    public Film create(Film film) {
        return filmDao.create(film);
    }

    @Override
    public Film update(Film film) {
        return filmDao.update(film);
    }

    @Override
    public Collection<Film> getFilms() {
        return filmDao.getFilms();
    }

    @Override
    public Film getFilmById(long id) {
        return filmDao.getFilmById(id);
    }
}
