package ru.yandex.practicum.filmorate.controller;
/*
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.service.user.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController controller;
    UserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserServiceImpl(userStorage);

    Set<Long> friends = new HashSet<>();
    User user = new User(1, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(1995, 12, 25), friends);
    User existingId = new User(1, "yandex@mail.ru", "tests", "",
            LocalDate.of(2023, 3, 25), friends);
    User nulId = new User(0, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(1995, 12, 25), friends);
    User updateUser = new User(1, "yandex@mail.ru", "tests", "test4",
            LocalDate.of(1999, 10, 2), friends);
    User futureBirthday = new User(0, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(2223, 4, 25), friends);

    @BeforeEach
    void createUserController() {
        controller = new UserController(userService);
        controller.create(user);
    }

    @Test
    void test1_createUserNulId() {
        User actual = controller.create(nulId);
        User expected = nulId;
        expected.setId(2);
        assertEquals(expected, actual, "Не удается создать пользователя");
    }

    @Test
    void test2_createUserExistingId() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(existingId);
                    }
                });
        assertEquals("Пользователь с заданным id уже существует!", exception.getMessage(),
                "Создает пользователей с уже существующим id");
    }

    @Test
    void test3_createUserFutureBirthday() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(futureBirthday);
                    }
                });
        assertEquals("Укажите корректную дату рождения!", exception.getMessage(),
                "Создает пользователей с днем рождения позже сегодняшнего дня");
    }

    //Обновление пользователей
    @Test
    void test4_updateUser() {
        User actual = controller.update(updateUser);
        User expected = updateUser;
        assertEquals(expected, actual, "Не удается обновить пользователя");
    }

    @Test
    void test5_updateUserNulId() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(nulId);
                    }
                });
        assertEquals("id пользователя не может быть равен 0!", exception.getMessage(),
                "Обновляет пользователей без указанного id");
    }

    @Test
    void test6_updateUserFutureBirthday() {
        futureBirthday.setId(1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(futureBirthday);
                    }
                });
        assertEquals("Укажите корректную дату рождения!", exception.getMessage(),
                "Обновляет пользователей с днем рождения позже сегодняшнего дня");
    }

    @Test
    void test7_getAllUsers() {
        existingId.setId(0);
        controller.create(existingId);
        List<User> actual = new ArrayList<>(controller.getUsers());
        List<User> expected = new ArrayList<>();
        expected.add(user);
        expected.add(existingId);
        assertEquals(expected, actual);
    }
}
 */