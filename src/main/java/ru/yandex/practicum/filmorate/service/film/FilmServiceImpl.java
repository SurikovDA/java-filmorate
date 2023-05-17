package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    //Добавление лайка
    @Override
    public void addLike(long filmId, long userId) {
        log.debug("Получен запрос на добавление лайка фильму");
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        film.getLikes().add(user.getId());
        log.debug("Лайк добавлен");
    }

    //Удаление лайка фильму
    @Override
    public void deleteLike(long filmId, long userId) {
        log.debug("Получен запрос на удаление лайка фильму");
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        film.getLikes().remove(user.getId());
        log.debug("Лайк к фильму удален");
    }

    //Получение 10 популярных фильмов по лайкам
    @Override
    public Collection<Film> getMostPopularFilms(Integer count) {
        log.debug("Получен запрос на получение популярных фильмов");
        Comparator<Film> comparator = Comparator.comparingInt(f -> f.getLikes().size());
        return filmStorage
                .getFilms()
                .stream()
                .sorted(comparator.reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    //Создание фильма
    @Override
    public Film create(Film film) {
        return filmStorage.create(film);
    }

    //Обновление фильма
    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    //Получение всех фильмов
    @Override
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    //Получение фильма по id
    @Override
    public Film getFilmById(long id) {
        return filmStorage.getFilmById(id);
    }
}
