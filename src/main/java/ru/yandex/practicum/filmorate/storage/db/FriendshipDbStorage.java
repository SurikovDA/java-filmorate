package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@RequiredArgsConstructor
@Component
@Primary
public class FriendshipDbStorage implements FriendshipDao {
    private final FriendshipDao friendshipDao;

    @Override
    public void create(long userId, long friendId) {
        friendshipDao.create(userId, friendId);
    }

    @Override
    public List<User> readFriendsByUserId(long userId) {
        return friendshipDao.readFriendsByUserId(userId);
    }

    @Override
    public List<User> getCommonFriends(long userId, long friendId) {
        return friendshipDao.getCommonFriends(userId, friendId);
    }

    @Override
    public void delete(long userId, long friendId) {
        friendshipDao.delete(userId, friendId);
    }
}
