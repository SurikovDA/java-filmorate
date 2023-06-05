package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.util.Collection;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre create(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO GENRE\n" +
                "(NAME)\n" +
                "VALUES(?);";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"GENRE_ID"});
            ps.setString(1, genre.getName());
            return ps;
        }, keyHolder);

        Long id = (Long) keyHolder.getKey().longValue();
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
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Genre.class)).stream().findAny()
                .orElse(null);
    }
}
