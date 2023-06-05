package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipStorage {

    void create(long userId, long friendId);

    List<User> readFriendsByUserId(long userId);

    List<User> getCommonFriends(long userId, long friendId);

    void delete(long userId, long friendId);
}
