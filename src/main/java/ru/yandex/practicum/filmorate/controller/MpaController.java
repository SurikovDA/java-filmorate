package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {
    MpaService mpaService;

    @Autowired
    MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/{id}")
    public Mpa get(@Valid @PathVariable int id) {
        log.info("Получен запрос GET /mpa/{}", id);
        return mpaService.getMpaById(id);
    }

    @GetMapping
    public List<Mpa> getAll() {
        log.info("Получен запрос GET /mpa");
        return new ArrayList<>(mpaService.readAll());
    }

    @PostMapping
    public Mpa create(@Valid @RequestBody Mpa mpa) {
        log.info("Получен запрос POST /mpa");
        return mpaService.create(mpa);
    }

    @PutMapping
    public Mpa update(@Valid @RequestBody Mpa mpa) {
        log.info("Получен запрос PUT /mpa");
        return mpaService.update(mpa);
    }

}
