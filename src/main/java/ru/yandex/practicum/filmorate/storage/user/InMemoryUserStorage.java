package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    //мапа для хранения пользователей
    private final Map<Long, User> users = new HashMap<>();
    public static long id = 1;

    @Autowired
    public InMemoryUserStorage() {
    }

    //Генерация id
    public long generateId(User user) {
        while (users.containsKey(id)) {
            id++;
        }
        log.debug("id пользователя сгенерирован");
        return id;
    }

    //Создание User
    @Override
    public User create(User user) {
        log.debug("Получен запрос на создание пользователя {}", user);
        if (users.containsKey(user.getId())) {
            log.warn("Пользователь с заданным id уже существует! Пользователь не создан {}!", user);
            throw new ValidationException("Пользователь с заданным id уже существует!");
        } else {
            user.setId(generateId(user));
            if ((user.getName() == null) || (user.getName().isBlank())) {
                log.debug("Установка имени пользователя с id = {}", user.getId());
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.info("Пользователь создан {}", user);
            return user;
        }
    }

    //Обновление User
    @Override
    public User update(User user) {
        log.debug("Получен запрос на обновление пользователя {}", user);
        if (!users.containsKey(user.getId())) {
            log.warn("Пользователь с id = {} не найден!", user.getId());
            throw new UserNotFoundException("Пользователь не найден!");
        } else {
            if (user.getName() == null) {
                log.debug("Установка имени пользователя с id = {}", user.getId());
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.info("Пользователь с id = {} обновлен", user.getId());
            return user;
        }
    }

    //Получение всех пользователей
    @Override
    public Collection<User> getUsers() {
        log.debug("Получен запрос на получение списка всех пользователей");
        return users.values();
    }

    //Получение пользователя по id
    @Override
    public User getUserById(long userId) {
        log.debug("Получен запрос на получение пользователя по id");
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            throw new UserNotFoundException("Такого пользователя не существует!");
        }
    }

    //Получение списка пользователей по списку id
    public Collection<User> getUsersById(Set<Long> idCommonFriends) {
        List<User> result = new ArrayList<>();
        for (long id : idCommonFriends) {
            if (!users.containsKey(id)) {
                throw new UserNotFoundException("Пользователя с указанным id не существует!");
            }
            result.add(users.get(id));
        }
        return result;
    }
}
