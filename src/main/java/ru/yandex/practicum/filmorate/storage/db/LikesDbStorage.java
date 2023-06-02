package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@RequiredArgsConstructor
@Component
@Primary
public class LikesDbStorage implements LikesDao {
    private final LikesDao likesDao;

    @Override
    public void create(long userId, long filmId) {
        likesDao.create(userId, filmId);
    }

    @Override
    public List<User> readLikesByFilmId(long filmId) {
        return likesDao.readLikesByFilmId(filmId);
    }

    @Override
    public List<Film> getPopularFilms(long count) {
        return likesDao.getPopularFilms(count);
    }

    @Override
    public void delete(long userId, long filmId) {
        likesDao.delete(userId, filmId);
    }
}
