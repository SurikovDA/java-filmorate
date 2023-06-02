package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public class LikesDaoImpl implements LikesDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(long userId, long filmId) {
        String sql = "INSERT INTO LIKES(USER_ID, FILM_ID)\n" +
                "VALUES(?, ?);";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public List<User> readLikesByFilmId(long filmId) {
        String sql = "SELECT * \n" +
                "FROM (SELECT LIKES.USER_ID \n" +
                "FROM LIKES\n" +
                "WHERE LIKES.FILM_ID = ?)\n" +
                "AS USERS_LIKE_BY_FILM \n" +
                "JOIN USERS ON USERS_LIKE_BY_FILM.USER_ID = USERS.ID;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), filmId);
    }

    @Override
    public List<Film> getPopularFilms(long count) {
        String sql = "SELECT f.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, count(l.USER_ID) rating\n" +
                "FROM FILMS f \n" +
                "left JOIN LIKES l ON l.FILM_ID = f.ID \n" +
                "GROUP BY f.id\n" +
                "ORDER BY rating DESC\n" +
                "LIMIT ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Film.class), count);
    }

    @Override
    public void delete(long userId, long filmId) {
        String sql = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sql, userId, filmId);
    }
}
