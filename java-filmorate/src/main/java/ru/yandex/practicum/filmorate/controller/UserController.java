package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    //мапа для хранения пользователей
    private final Map<Integer, User> users = new HashMap<>();
    public static int id = 1;

    //Генерация id
    public int generateId(User user) {
        log.debug("Началась генерация id пользователя");
        while (users.containsKey(id)) {
            id++;
        }
        log.debug("id пользователя сгенерирован");
        return id;
    }

    //Создание User
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Получен запрос на создание пользователя {}", user);
        if ((user.getBirthday().isAfter(LocalDate.now()))
                || (user.getEmail().isBlank())
                || (user.getId() < 0)
                || (user.getLogin().isBlank())
                || (users.containsKey(user.getId()))) {
            log.warn("Пользователь не создан {} !", user);
            throw new ValidationException("Пользователь не создан!");
        } else {
            if (user.getName() == null) {
                log.debug("Установка имени пользователя {}", user);
                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(generateId(user));
            }
            users.put(user.getId(), user);
            log.debug("Пользователь создан {}", user);
            return user;
        }
    }

    //Обновление User
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.debug("Получен запрос на обновление пользователя {}", user);
        if ((user.getBirthday().isAfter(LocalDate.now()))
                || (user.getEmail().isBlank())
                || (user.getId() < 0)
                || (user.getLogin().isBlank())
                || (!users.containsKey(user.getId()))) {
            log.warn("Пользователь не обновлен {} !", user);
            throw new ValidationException("Пользователь не обновлен!");
        } else {
            if (user.getName().isBlank()) {
                log.debug("Установка имени пользователя {}", user);
                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(generateId(user));
            }
            users.put(user.getId(), user);
            log.debug("Пользователь обновлен {}", user);
            return user;
        }
    }

    //Получение всех пользователей
    @GetMapping
    public Collection<User> getUsers() {
        log.debug("Получен запрос на получение списка всех пользователей");
        return users.values();
    }
}