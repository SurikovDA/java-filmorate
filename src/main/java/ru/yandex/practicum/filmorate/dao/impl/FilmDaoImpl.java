package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Component
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        jdbcTemplate.update("INSERT INTO films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)\n" +
                        " VALUES (?, ?, ?, ?, ?)",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        Long id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM FILMS", Long.class);
        film.setId(id);
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        return jdbcTemplate.query("SELECT * " +
                "FROM FILMS ", new BeanPropertyRowMapper<>(Film.class));
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update("UPDATE FILMS " +
                        "SET name=?, description=?, release_date=?, duration=?, mpa_id=? " +
                        "WHERE id=?;",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public Film getFilmById(long id) {
        return jdbcTemplate.query("SELECT F.* " +
                                "FROM FILMS AS F " +
                                "JOIN MPA AS M ON F.MPA_ID = M.MPA_ID " +
                                "WHERE F.ID = ? ",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Film.class)).stream()
                .findAny().orElse(null);
    }

}
