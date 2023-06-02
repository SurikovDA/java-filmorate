package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final FriendshipDao friendshipDao;

    @Autowired
    public UserServiceImpl(UserStorage userStorage, FriendshipDao friendshipDao) {
        this.userStorage = userStorage;
        this.friendshipDao = friendshipDao;
    }

    //Добавление в друзья
    @Override
    public void addFriend(long userId, long friendId) {
        log.debug("Началась обработка добавления в друзья");
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        if (user == null || friend == null) {
            throw new UserNotFoundException("Не найдены пользователи с указанными id!");
        }
        friendshipDao.create(user.getId(), friend.getId());
        log.debug("Друг добавлен");
    }

    //Удаление из друзей
    @Override
    public void deleteFromFriends(long userId, long friendId) {
        log.debug("Началась обработка удаления из друзей");
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        friendshipDao.delete(user.getId(), friend.getId());
        log.debug("Пользователь удален из друзей");
    }

    //Получение списка общих друзей
    @Override
    public Collection<User> getCommonFriends(long userId, long friendId) {
        log.debug("Получен запрос на получение списка общих друзей");
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        return friendshipDao.getCommonFriends(user.getId(), friend.getId());
    }

    //Получение списка друзей
    @Override
    public Collection<User> getFriends(long userId) {
        if (userStorage.getUserById(userId) != null) {
            return friendshipDao.readFriendsByUserId(userId);
        } else {
            throw new UserNotFoundException("Пользователя с указанным id не существует!");
        }
    }

    //Создание пользователя
    @Override
    public User create(User user) {
        log.debug("Началось создание пользователя");
        if (userStorage.getUserById(user.getId()) != null) {
            throw new ValidationException("Пользователь с таким id уже есть в базе");
        }
        if ((user.getName() == null) || (user.getName().isBlank())) {
            log.debug("Установка имени пользователя с id = {}", user.getId());
            user.setName(user.getLogin());
        }
        User createUser = userStorage.create(user);
        log.debug("Пользователь {} создан", createUser);
        return createUser;
    }

    //Обновление пользователя
    @Override
    public User update(User user) {
        log.debug("Началось обновление пользователя с id = {}", user.getId());
        if (userStorage.getUserById(user.getId()) != null) {
            return userStorage.update(user);
        } else {
            log.info("Пользователь с id = {} не найден", user.getId());
            throw new UserNotFoundException("Пользователь с указанным id не найден!");
        }
    }

    //Получение всех пользователей
    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    //Получение пользователя по id
    @Override
    public User getUserById(long userId) {
        log.debug("Начался поиск пользователя по id = {}", userId);
        if (userStorage.getUserById(userId) != null) {
            return userStorage.getUserById(userId);
        } else {
            log.debug("Пользователь с id = {} существует", userId);
            throw new UserNotFoundException("Пользователь с указанным id не найден!");
        }
    }

    //Получение списка пользователей по списку id
    @Override
    public Collection<User> getUsersById(Set<Long> idCommonFriends) {
        List<User> result = new ArrayList<>();
        for (long id : idCommonFriends) {
            User user = userStorage.getUserById(id);
            if (user != null) {
                result.add(user);
            } else {
                throw new UserNotFoundException("Пользователь не найден");
            }
        }
        return result;
    }
}
