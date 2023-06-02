package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaDao mpaDao;
    private final LikesDao likesDao;
    private final GenreDao genreDao;

    private final FilmGenreDao filmGenreDao;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage,
                           MpaDao mpaDao, LikesDao likesDao, GenreDao genreDao, FilmGenreDao filmGenreDao) {
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
        Collection<Film> popularFilms = likesDao.getPopularFilms(count);
        for (Film film : popularFilms) {
            film.setMpa(mpaDao.getMpaByFilmId(film.getId()));
            film.setGenres(filmGenreDao.readGenresByFilmId(film.getId()));
        }
        return popularFilms;
    }

    //Создание фильма
    @Override
    public Film create(Film film) {
        if (filmStorage.getFilmById(film.getId()) != null) {
            throw new ValidationException("Фильм с указанным id уже существует!");
        } else {
            Film newFilm = filmStorage.create(film);
            log.debug("Фильм с id = {} добавлен в бд", film.getId());
            newFilm.setMpa(mpaDao.getMpaById(film.getMpa().getId()));
            if (film.getGenres() != null) {
                addFilmGenre(newFilm.getId(), new HashSet<>(film.getGenres()));
                newFilm.setGenres(filmGenreDao.readGenresByFilmId(film.getId()));
            }
            return newFilm;
        }
    }

    void addFilmGenre(long filmId, Set<Genre> genres) {
        genres.forEach((genre) -> filmGenreDao.create(filmId, genre.getId()));
    }

    //Обновление фильма
    @Override
    public Film update(Film film) {
        if (filmStorage.getFilmById(film.getId()) != null) {
            Film updateFilm = filmStorage.update(film);
            updateFilm.setMpa(mpaDao.getMpaById(film.getMpa().getId()));
            if (film.getGenres() != null) {
                filmGenreDao.delete(film.getId());
                addFilmGenre(updateFilm.getId(), new HashSet<>(film.getGenres()));
                updateFilm.setGenres(filmGenreDao.readGenresByFilmId(film.getId()));
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
            getFilm.setMpa(mpaDao.getMpaByFilmId(getFilm.getId()));
            getFilm.setGenres(filmGenreDao.readGenresByFilmId(id));
            return getFilm;
        } else {
            throw new FilmNotFoundException("Фильма с указанным id не существует");
        }
    }
}