package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@RequiredArgsConstructor
@Component
@Primary
public class MpaDbStorage implements MpaDao {
    private final MpaDao mpaDao;
    @Override
    public Mpa create(Mpa mpa) {
        return mpaDao.create(mpa);
    }

    @Override
    public List<Mpa> readAll() {
        return mpaDao.readAll();
    }

    @Override
    public Mpa update(Mpa mpa) {
        return mpaDao.update(mpa);
    }

    @Override
    public Mpa getMpaById(int id) {
        return mpaDao.getMpaById(id);
    }

    @Override
    public Mpa getMpaByFilmId(long id) {
        return mpaDao.getMpaByFilmId(id);
    }
}
