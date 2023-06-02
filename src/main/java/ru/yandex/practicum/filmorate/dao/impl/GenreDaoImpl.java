package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre create(Genre genre) {
        String sql = "INSERT INTO GENRE\n" +
                "(NAME)\n" +
                "VALUES(?);";
        jdbcTemplate.update(sql, genre.getName());
        Long id = jdbcTemplate.queryForObject("SELECT MAX(GENRE_ID) FROM GENRE", Long.class);
        genre.setId(id);
        return genre;
    }

    @Override
    public Collection<Genre> readAll() {
        String sql = "SELECT G.GENRE_ID AS ID, G.NAME " +
                "FROM GENRE G";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Genre update(Genre genre) {
        String sql = "UPDATE GENRE " +
                "SET NAME = ?" +
                "WHERE GENRE_ID = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());
        return genre;
    }

    @Override
    public Genre getGenreById(long id) {
        String sql = "SELECT G.GENRE_ID AS ID, G.NAME " +
                "FROM GENRE G " +
                "WHERE G.GENRE_ID = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Genre.class)).stream().findAny().
                orElse(null);
    }
}
