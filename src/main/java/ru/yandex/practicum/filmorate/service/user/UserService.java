package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    public User addFriend(long userId, long friendId);

    public User deleteFromFriends(long userId, long friendId);

    public Collection<User> getCommonFriends(long userId, long friendId);

    public Collection<User> getFriends(long userId);
}
