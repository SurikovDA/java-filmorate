package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserService {
    public User addFriend(long userId, long friendId);

    public User deleteFromFriends(long userId, long friendId);

    public Collection<User> getCommonFriends(long userId, long friendId);

    public Collection<User> getFriends(long userId);

    public User create(User user);

    public User update(User user);

    public Collection<User> getUsers();

    public User getUserById(long userId);

    public Collection<User> getUsersById(Set<Long> idCommonFriends);
}
