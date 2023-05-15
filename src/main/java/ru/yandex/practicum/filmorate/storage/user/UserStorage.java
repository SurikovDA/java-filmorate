package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    public User create(User user);

    public User update(User user);

    public Collection<User> getUsers();

    public User getUserById(long userId);

    public Collection<User> getUsersById(Set<Long> idCommonfriends);
}
