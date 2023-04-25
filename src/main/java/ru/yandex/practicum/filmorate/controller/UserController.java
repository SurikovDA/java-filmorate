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
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения указана не корректно! Пользователь не создан {} !", user);
            throw new ValidationException("Укажите корректную дату рождения!");
        } else if (users.containsKey(user.getId())) {
            log.warn("Пользователь с заданным id уже существует! Пользователь не создан {}!", user);
            throw new ValidationException("Пользователь с заданным id уже существует!");
        } else {
            user.setId(generateId(user));
            if (user.getName() == null) {
                log.debug("Установка имени пользователя с id = {}", user.getId());
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.info("Пользователь создан {}", user);
            return user;
        }
    }

    //Обновление User
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.debug("Получен запрос на обновление пользователя {}", user);
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения указана не корректно! Пользователь не обновлен {} !", user);
            throw new ValidationException("Укажите корректную дату рождения!");
        } else if (!users.containsKey(user.getId())) {
            log.warn("Пользователь с id = {} не найден!", user.getId());
            throw new ValidationException("Пользователь не найден!");
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
    @GetMapping
    public Collection<User> getUsers() {
        log.debug("Получен запрос на получение списка всех пользователей");
        return users.values();
    }
}
