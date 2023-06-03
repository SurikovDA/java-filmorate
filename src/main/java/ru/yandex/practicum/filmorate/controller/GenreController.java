package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/genres")
public class GenreController {

    GenreService genreService;

    @Autowired
    GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public Genre get(@Valid @PathVariable int id) {
        log.info("Получен запрос GET /genres/{}", id);
        return genreService.getGenreById(id);
    }

    @GetMapping
    public List<Genre> getAll() {
        log.info("Получен запрос GET /genres");
        return new ArrayList<>(genreService.readAll());
    }

    @PostMapping
    public Genre create(@Valid @RequestBody Genre genre) {
        log.info("Получен запрос POST /genres");
        return genreService.create(genre);
    }

    @PutMapping
    public Genre update(@Valid @RequestBody Genre genre) {
        log.info("Получен запрос PUT /genres");
        return genreService.update(genre);
    }
}
