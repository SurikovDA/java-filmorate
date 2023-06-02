package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
public class FilmGenreDaoImpl implements FilmGenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(long filmId, long genreId) {
        String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES(?, ?)";
        jdbcTemplate.update(sql, filmId, genreId);
    }

    @Override
    public List<Genre> readGenresByFilmId(long filmId) {
        String sql = "SELECT GENRE.GENRE_ID AS ID, GENRE.NAME\n" +
                "FROM GENRE\n" +
                "JOIN FILM_GENRE ON GENRE.GENRE_id = FILM_GENRE.genre_id\n" +
                "WHERE FILM_GENRE.film_id = ?\n" +
                "ORDER BY GENRE.GENRE_ID";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class), filmId);
    }

    @Override
    public void delete(long filmId) {
        String sql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }
}
