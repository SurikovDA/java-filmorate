package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO users\n" +
                "(NAME, LOGIN, EMAIL, BIRTHDAY)\n" +
                "VALUES(?, ?, ?, ?);";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);

        Long id = (Long) keyHolder.getKey().longValue();
        user.setId(id);
        return user;
    }

    @Override
    public List<User> readAll() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE USERS\n" +
                "SET NAME=?, LOGIN=?, EMAIL=?, BIRTHDAY=?\n" +
                "WHERE ID=?;";
        jdbcTemplate.update(sql, user.getName(),
                user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public User getUserById(long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }
}
