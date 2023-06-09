package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaDao;
    private final LikesStorage likesDao;
    private final GenreStorage genreDao;

    private final FilmGenreStorage filmGenreDao;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage,
                           MpaStorage mpaDao, LikesStorage likesDao, GenreStorage genreDao, FilmGenreStorage filmGenreDao) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.mpaDao = mpaDao;
        this.likesDao = likesDao;
        this.genreDao = genreDao;
        this.filmGenreDao = filmGenreDao;
    }

    //Добавление лайка
    @Override
    public void addLike(long filmId, long userId) {
        log.debug("Получен запрос на добавление лайка фильму");
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        likesDao.create(user.getId(), film.getId());
        log.debug("Лайк добавлен");
    }

    //Удаление лайка фильму
    @Override
    public void deleteLike(long filmId, long userId) {
        log.debug("Получен запрос на удаление лайка фильму");
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        likesDao.delete(user.getId(), film.getId());
        log.debug("Лайк к фильму удален");
    }

    //Получение 10 популярных фильмов по лайкам
    @Override
    public Collection<Film> getMostPopularFilms(Integer count) {
        log.debug("Получен запрос на получение популярных фильмов");
        return likesDao.getPopularFilms(count);
    }

    //Создание фильма
    @Override
    public Film create(Film film) {
        if (filmStorage.getFilmById(film.getId()) != null) {
            throw new ValidationException("Фильм с указанным id уже существует!");
        } else {
            Film newFilm = filmStorage.create(film);
            log.debug("Фильм с id = {} добавлен в бд", film.getId());
            if (film.getGenres() != null) {
                newFilm.setGenres(filmGenreDao.setGenresFilm(newFilm.getId(), new HashSet<>(film.getGenres())));
            }
            return newFilm;
        }
    }

    //Обновление фильма
    @Override
    public Film update(Film film) {
        if (filmStorage.getFilmById(film.getId()) != null) {
            Film updateFilm = filmStorage.update(film);
            updateFilm.setMpa(mpaDao.getMpaById(film.getMpa().getId()));
            if (film.getGenres() != null) {
                filmGenreDao.delete(film.getId());
                updateFilm.setGenres(filmGenreDao.setGenresFilm(updateFilm.getId(), new HashSet<>(film.getGenres())));
            }
            return updateFilm;
        } else {
            throw new FilmNotFoundException("Фильма с указанным id не существует");
        }
    }

    //Получение всех фильмов
    @Override
    public Collection<Film> getFilms() {
        Collection<Film> allFilms = filmStorage.getFilms();
        for (Film film : allFilms) {
            film.setMpa(mpaDao.getMpaByFilmId(film.getId()));
            film.setGenres(filmGenreDao.readGenresByFilmId(film.getId()));
        }
        return allFilms;
    }

    //Получение фильма по id
    @Override
    public Film getFilmById(long id) {
        if (filmStorage.getFilmById(id) != null) {
            Film getFilm = filmStorage.getFilmById(id);
            return getFilm;
        } else {
            throw new FilmNotFoundException("Фильма с указанным id не существует");
        }
    }
}