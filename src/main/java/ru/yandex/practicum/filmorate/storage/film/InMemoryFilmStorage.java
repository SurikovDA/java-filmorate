package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    //Мапа для хранения фильмов
    private final Map<Long, Film> films = new HashMap<>();
    public static long id = 1;

    @Autowired
    public InMemoryFilmStorage() {
    }

    //Генерация id
    public long generateIdFilm(Film film) {
        while (films.containsKey(id)) {
            id++;
        }
        log.debug("id фильма сгенерирован");
        return id;
    }

    //Создание фильма
    @Override
    public Film create(Film film) {
        log.debug("Запрос на создание фильма получен {}", film);
        if (films.containsKey(film.getId())) {
            log.warn("Фильм не создан. Фильм с id = {} уже существует", film.getId());
            throw new ValidationException("Фильм с указанным id уже существует!");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Фильм с id = {} не создан. Дата выпуска фильма не может быть раньше даты создания кино!",
                    film.getId());
            throw new ValidationException("Фильм не создан. Дата выпуска фильма не может быть раньше даты" +
                    " создания кино!");
        } else {
            film.setId(generateIdFilm(film));
            films.put(film.getId(), film);
            log.info("Фильм с id = {} создан", film.getId());
            return film;
        }
    }

    //Обновление фильма
    @Override
    public Film update(Film film) {
        log.debug("Запрос на обновление фильма получен {}", film);
        if ((!films.containsKey(film.getId()))) {
            log.warn("Фильм с id = {} не найден!", film.getId());
            throw new FilmNotFoundException("Фильм с указанным id не найден!");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Фильм с id = {} не обновлен. Дата выпуска фильма не может быть раньше даты создания кино!",
                    film.getId());
            throw new ValidationException("Фильм не обновлен. Дата выпуска фильма не может быть раньше даты" +
                    " создания кино!");
        } else {
            films.put(film.getId(), film);
            log.info("Фильм с id = {} обновлен", film.getId());
            return film;
        }
    }

    //Получение фильма по id
    @Override
    public Film getFilmById(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new FilmNotFoundException("Такого фильма не существует!");
        }
    }

    //Получение всех фильмов
    @Override
    public Collection<Film> getFilms() {
        log.debug("Запрос на список фильмов получен");
        return films.values();
    }
}
