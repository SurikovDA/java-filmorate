package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    //Мапа для хранения фильмов
    private final Map<Integer, Film> films = new HashMap<>();
    public static int id = 1;
    //Генерация id
    public int generateIdFilm(Film film) {
        log.debug("Началась генерация id фильма");
        while (films.containsKey(id)) {
            id++;
        }
        log.debug("id фильма сгенерирован");
        return id;
    }
    //Создание фильма
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Запрос на создание фильма получен {}", film);
        if ((film.getName().isBlank())
                || (film.getDescription().length() > 200)
                || (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
                || (films.containsValue(film))
                || (films.containsKey(film.getId()))
                || (film.getId() < 0)
                || (film.getDuration() < 0)) {
            log.warn("Фильм не создан {}", film);
            throw new ValidationException("Новый фильм не создан!");
        } else {
            if (film.getId() == 0) {
                film.setId(generateIdFilm(film));
            }
            films.put(film.getId(), film);
            log.debug("Фильм создан {}", film);
            return film;
        }
    }
    //Обновление фильма
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Запрос на обновление фильма получен {}", film);
        if ((film.getName().isBlank())
                || (film.getDescription().length() > 200)
                || (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
                || (film.getId() < 0)
                || (film.getDuration() < 0)
                || (!films.containsKey(film.getId()))) {
            log.warn("Фильм не обновлен {} !", film);
            throw new ValidationException("Фильм не обновлен!");
        } else {
            if (film.getId() == 0) {
                film.setId(generateIdFilm(film));
            }
            films.put(film.getId(), film);
            log.debug("Фильм обновлен {}", film);
            return film;
        }
    }
    //Получение всех фильмов
    @GetMapping
    public Collection<Film> getFilms() {
        log.debug("Запрос на список фильмов получен");
        return films.values();
    }
}