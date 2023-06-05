package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaDao;
    private final FilmGenreStorage filmGenreDao;

    @Autowired
    public LikesDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaDao, FilmGenreStorage filmGenreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaDao = mpaDao;
        this.filmGenreDao = filmGenreDao;
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
        String sql = "SELECT f.*, count(l.USER_ID) rating\n" +
                "FROM FILMS f \n" +
                "left JOIN LIKES l ON l.FILM_ID = f.ID \n" +
                "GROUP BY f.id\n" +
                "ORDER BY rating DESC\n" +
                "LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToFilm(rs), count).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long userId, long filmId) {
        String sql = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sql, userId, filmId);
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
                .rating(resultSet.getInt("rating"))
                .genres(filmGenreDao.readGenresByFilmId(resultSet.getLong("ID")))
                .build();
    }
}
