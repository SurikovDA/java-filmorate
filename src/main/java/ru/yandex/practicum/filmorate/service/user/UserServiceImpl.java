package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    //Добавление в друзья
    @Override
    public User addFriend(long userId, long friendId) {
        log.debug("Началась обработка добавления в друзья");
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        log.debug("Добавлены в друзья друг другу");
        return friend;
    }

    //Удаление из друзей
    @Override
    public User deleteFromFriends(long userId, long friendId) {
        log.debug("Началась обработка удаления из друзей");
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.debug("Пользователь удален из друзей");
        return userStorage.getUserById(friendId);
    }

    //Получение списка общих друзей
    @Override
    public Collection<User> getCommonFriends(long userId, long friendId) {
        log.debug("Получен запрос на получение списка общих друзей");
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        Set<Long> commonFriends = user.getFriends().stream()
                .filter(friend.getFriends()::contains)
                .collect(Collectors.toSet());
        return userStorage.getUsersById(commonFriends);
    }

    //Получение списка друзей
    @Override
    public Collection<User> getFriends(long userId) {
        Set<Long> friends = userStorage.getUserById(userId).getFriends();
        return userStorage.getUsersById(friends);
    }
}
