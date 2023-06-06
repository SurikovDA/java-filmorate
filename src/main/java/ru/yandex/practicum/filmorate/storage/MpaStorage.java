package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    Mpa create(Mpa mpa);

    List<Mpa> readAll();

    Mpa update(Mpa mpa);

    Mpa getMpaById(int id);

    Mpa getMpaByFilmId(long id);
}
