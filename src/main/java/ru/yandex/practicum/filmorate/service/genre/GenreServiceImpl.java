package ru.yandex.practicum.filmorate.service.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.Collection;

@Component
@Slf4j
public class GenreServiceImpl implements GenreService {
    GenreDbStorage genreDbStorage;

    @Autowired
    GenreServiceImpl(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    @Override
    public Genre create(Genre genre) {
        validate(genre);
        if (genreDbStorage.getGenreById(genre.getId()) != null) {
            throw new ValidationException("Жанр с таким id уже есть в базе");
        }
        return genreDbStorage.create(genre);
    }

    @Override
    public Collection<Genre> readAll() {
        return genreDbStorage.readAll();
    }

    @Override
    public Genre update(Genre genre) {
        validate(genre);
        if (genreDbStorage.getGenreById(genre.getId()) != null) {
            log.info("Жанр с id '{}' обновлен", genre.getId());
            return genreDbStorage.update(genre);
        } else {
            log.info("EntityNotFoundException (Жанр не может быть обновлен, т.к. его нет в списке)");
            throw new EntityNotFoundException("Жанр не может быть обновлен, т.к. его нет в списке");
        }
    }

    @Override
    public Genre getGenreById(int id) {
        if (genreDbStorage.getGenreById(id) != null) {
            return genreDbStorage.getGenreById(id);
        } else {
            throw new EntityNotFoundException(String.format("Жанра с id=%d нет в списке", id));
        }
    }

    private void validate(Genre genre) {
        if (genre.getName().isEmpty() || genre.getName().isBlank()) {
            log.info("ValidationException (Пустое название жанра)");
            throw new ValidationException("Пустое название жанра");
        }
        if (genre.getId() < 0) {
            log.info("ValidationException (Значение id не может быть отрицательным)");
            throw new ValidationException("Значение id не может быть отрицательным");
        }
    }
}
