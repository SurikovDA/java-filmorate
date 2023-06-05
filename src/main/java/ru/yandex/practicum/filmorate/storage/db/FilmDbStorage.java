package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmGenreStorage filmGenreDao;
    private final LikesStorage likesDao;

    private final MpaStorage mpaDao;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmGenreStorage filmGenreDao, MpaStorage mpaDao, LikesStorage likesDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreDao = filmGenreDao;
        this.mpaDao = mpaDao;
        this.likesDao = likesDao;
    }

    @Override
    public Film create(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)\n" +
                " VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);

        Long id = (Long) keyHolder.getKey().longValue();
        film.setId(id);
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "SELECT * " +
                "FROM FILMS ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToFilm(rs)).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
        String sql = "SELECT * " +
                "FROM FILMS AS F " +
                "JOIN MPA AS M ON F.MPA_ID = M.MPA_ID " +
                "WHERE F.ID = ? ";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToFilm(rs), id).stream()
                .findAny().orElse(null);
    }

    private Film mapRowToFilm(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(mpaDao.getMpaById(resultSet.getInt("MPA_ID")))
                .likes(new HashSet<>())
                .genres(filmGenreDao.readGenresByFilmId(resultSet.getLong("ID")))
                .build();
    }

}
