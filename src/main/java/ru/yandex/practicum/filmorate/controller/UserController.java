package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Создание User
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения указана не корректно! Пользователь не создан {} !", user);
            throw new ValidationException("Укажите корректную дату рождения!");
        } else {
            return userService.create(user);
        }
    }

    //Обновление User
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения указана не корректно! Пользователь не обновлен {} !", user);
            throw new ValidationException("Укажите корректную дату рождения!");
        } else if (user.getId() == 0) {
            log.warn("id пользователя указан 0!");
            throw new ValidationException("id пользователя не может быть равен 0!");
        } else {
            return userService.update(user);
        }
    }

    //Получение всех пользователей
    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    //Получить пользователя по id
    @GetMapping("{id}")
    public User user(@PathVariable long id) {
        return userService.getUserById(id);
    }

    //Добавить в друзья
    @PutMapping("{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
    }

    //Удалить из друзей
    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFromFriends(id, friendId);
    }

    //Получить список друзей
    @GetMapping("{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    //Получить список общих друзей
    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
