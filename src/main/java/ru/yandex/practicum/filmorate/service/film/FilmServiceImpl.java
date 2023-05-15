package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    //Добавление лайка
    @Override
    public void addLike(long filmId, long userId) {
        log.debug("Получен запрос на добавление лайка фильму");
        filmStorage.getFilms().stream()
                .filter(film -> film.getId() == filmId)
                .findFirst()
                .get()
                .getLikes()
                .add(userId);
        log.debug("Лайк добавлен");
    }

    //Удаление лайка фильму
    @Override
    public void deleteLike(long filmId, long userId) {
        log.debug("Получен запрос на удаление лайка фильму");
        if (userId < 0) {
            throw new UserNotFoundException("id указан меньше 0!");
        } else if (filmId < 0) {
            throw new FilmNotFoundException("id фильма не может быть отрицательным!");
        }
        filmStorage.getFilms().stream()
                .filter(film -> film.getId() == filmId)
                .findFirst()
                .get()
                .getLikes()
                .remove(userId);
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
                .collect(Collectors.toSet());
    }
}
