package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    //Создание фильма
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Фильм с id = {} не создан. Дата выпуска фильма не может быть раньше даты создания кино!",
                    film.getId());
            throw new ValidationException("Фильм не создан. Дата выпуска фильма не может быть раньше даты" +
                    " создания кино!");
        } else {
            return filmService.create(film);
        }
    }

    //Обновление фильма
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Фильм с id = {} не обновлен. Дата выпуска фильма не может быть раньше даты создания кино!",
                    film.getId());
            throw new ValidationException("Фильм не обновлен. Дата выпуска фильма не может быть раньше даты" +
                    " создания кино!");
        } else {
            return filmService.update(film);
        }
    }

    //Получение фильма по id
    @GetMapping("{id}")
    public Film getFilm(@PathVariable long id) {
        return filmService.getFilmById(id);
    }

    //Получение всех фильмов
    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    //Поставить лайк
    @PutMapping("{filmId}/like/{userId}")
    public void addLike(@PathVariable long filmId, @PathVariable long userId) {
        if (userId < 0) {
            throw new UserNotFoundException("id указан меньше 0!");
        } else if (filmId < 0) {
            throw new FilmNotFoundException("id фильма не может быть отрицательным!");
        } else {
            filmService.addLike(filmId, userId);
        }
    }

    //Удалить лайк
    @DeleteMapping("{filmId}/like/{userId}")
    public void unlike(@PathVariable long filmId, @PathVariable long userId) {
        if (userId < 0) {
            throw new UserNotFoundException("id указан меньше 0!");
        } else if (filmId < 0) {
            throw new FilmNotFoundException("id фильма не может быть отрицательным!");
        } else {
            filmService.deleteLike(filmId, userId);
        }
    }

    //Список лучших фильмов
    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getMostPopularFilms(count);
    }
}