package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaDao {
    Mpa create(Mpa mpa);

    List<Mpa> readAll();

    Mpa update(Mpa mpa);

    Mpa getMpaById(int id);

    Mpa getMpaByFilmId(long id);
}
