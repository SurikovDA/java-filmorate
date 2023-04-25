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
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Запрос на обновление фильма получен {}", film);
        if ((!films.containsKey(film.getId()))) {
            log.warn("Фильм с id = {} не найден!", film.getId());
            throw new ValidationException("Фильм с указанным id не найден!");
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

    //Получение всех фильмов
    @GetMapping
    public Collection<Film> getFilms() {
        log.debug("Запрос на список фильмов получен");
        return films.values();
    }
}