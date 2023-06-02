package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Component
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa create(Mpa mpa) {
        String sql = "INSERT INTO MPA\n" +
                "(NAME, DESCRIPTION)\n" +
                "VALUES(?, ?);";
        jdbcTemplate.update(sql, mpa.getName(), mpa.getDescription());
        Integer id = jdbcTemplate.queryForObject("SELECT MAX(MPA_ID) FROM MPA", Integer.class);
        mpa.setId(id);
        return mpa;
    }

    @Override
    public List<Mpa> readAll() {
        String sql = "SELECT M.MPA_ID AS ID, M.NAME, M.DESCRIPTION " +
                "FROM MPA M";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Mpa.class));
    }

    @Override
    public Mpa update(Mpa mpa) {
        String sql = "UPDATE MPA\n" +
                "SET NAME=?, DESCRIPTION=?\n" +
                "WHERE MPA_ID=?;";
        jdbcTemplate.update(sql, mpa.getName(), mpa.getDescription(), mpa.getId());
        return mpa;
    }

    @Override
    public Mpa getMpaById(int id) {
        String sql = "SELECT M.MPA_ID AS ID, M.NAME, M.DESCRIPTION \n" +
                "FROM MPA M " +
                "WHERE M.MPA_ID = ?;";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Mpa.class)).
                stream().findAny().orElse(null);
    }

    @Override
    public Mpa getMpaByFilmId(long id) {
        String sql = "SELECT m.MPA_ID AS ID, M.NAME, M.DESCRIPTION \n" +
                "FROM MPA m \n" +
                "JOIN FILMS f ON  m.MPA_ID = f.MPA_ID\n" +
                "WHERE f.ID = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Mpa.class)).
                stream().
                findAny().
                orElse(null);
    }
}
