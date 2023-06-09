package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public class FriendshipDbStorage implements FriendshipStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(long userId, long friendId) {
        String sql = "INSERT INTO FRIENDSHIPS " +
                "(USER_ID, FRIEND_ID)\n" +
                "VALUES(?, ?);";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> readFriendsByUserId(long userId) {
        String sql = "SELECT u.id, u.name, u.email, u.login, u.birthday\n" +
                "FROM FRIENDSHIPS f\n" +
                "JOIN USERS u ON f.FRIEND_ID = U.ID \n" +
                "AND F.USER_ID=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public List<User> getCommonFriends(long userId, long friendId) {
        String sql = "SELECT * \n" +
                "FROM USERS u \n" +
                "WHERE id \n" +
                "IN (SELECT FRIEND_ID FROM FRIENDSHIPS f  WHERE user_id = ?)\n" +
                "AND id IN (SELECT FRIEND_ID FROM FRIENDSHIPS WHERE user_id = ?);";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId, friendId);
    }

    @Override
    public void delete(long userId, long friendId) {
        String sql = "DELETE FROM FRIENDSHIPS\n" +
                "WHERE FRIEND_ID=? AND USER_ID=?;\n";
        jdbcTemplate.update(sql, friendId, userId);
    }
}
