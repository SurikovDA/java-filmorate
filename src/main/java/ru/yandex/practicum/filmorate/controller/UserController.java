package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    //Создание User
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userStorage.create(user);
    }

    //Обновление User
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userStorage.update(user);
    }

    //Получение всех пользователей
    @GetMapping
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    //Получить пользователя по id
    @GetMapping("{id}")
    public User user(@PathVariable long id) {
        return userStorage.getUserById(id);
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
