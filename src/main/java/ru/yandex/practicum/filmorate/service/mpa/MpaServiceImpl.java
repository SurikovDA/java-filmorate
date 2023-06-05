package ru.yandex.practicum.filmorate.service.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

import java.util.List;

@Component
@Slf4j
public class MpaServiceImpl implements MpaService {

    MpaDbStorage mpaDbStorage;

    @Autowired
    MpaServiceImpl(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    @Override
    public Mpa create(Mpa mpa) {
        if (mpaDbStorage.getMpaById(mpa.getId()) != null) {
            throw new ValidationException("MPA с таким id уже есть в базе");
        }
        log.info("MPA с id '{}' добавлен в список", mpa.getId());
        return mpaDbStorage.create(mpa);
    }

    @Override
    public List<Mpa> readAll() {
        return mpaDbStorage.readAll();
    }

    @Override
    public Mpa update(Mpa mpa) {
        if (mpaDbStorage.getMpaById(mpa.getId()) != null) {
            log.info("MPA с id '{}' обновлен", mpa.getId());
            return mpaDbStorage.update(mpa);
        } else {
            log.info("EntityNotFoundException (MPA не может быть обновлен, т.к. его нет в списке)");
            throw new EntityNotFoundException("MPA не может быть обновлен, т.к. его нет в списке");
        }
    }

    @Override
    public Mpa getMpaById(int id) {
        if (mpaDbStorage.getMpaById(id) != null) {
            return mpaDbStorage.getMpaById(id);
        } else {
            throw new EntityNotFoundException(String.format("MPA с id=%d нет в списке", id));
        }
    }
}
