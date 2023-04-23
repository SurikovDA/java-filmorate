package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController controller;
    User user = new User(1, "yandex@mail.ru", "tests", "test1"
            , LocalDate.of(1995, 12, 25));
    User gran = new User(0, "yandex@mail.ru", "tests", ""
            , LocalDate.of(2023, 3, 25));
    User emailError = new User(0, "", "tests", "test1"
            , LocalDate.of(1995, 12, 25));
    User loginNull = new User(0, "yandex@mail.ru", " ", "test1"
            , LocalDate.of(1995, 12, 25));
    User nameNull = new User(0, "yandex@mail.ru", "tests", ""
            , LocalDate.of(1995, 12, 25));
    User futureBirthday = new User(0, "yandex@mail.ru", "tests", "test1"
            , LocalDate.of(2223, 4, 25));

    @BeforeEach
    void createUserController() {
        controller = new UserController();
        controller.create(user);
    }

    @Test
    void test1_generateIdUser() {
        int factId = controller.generateId(user);
        int expectedId = 2;
        assertEquals(expectedId, factId, "Не верно сгенерирован id");
    }

    @Test
    void test2_createUserGran() {
        User actual = controller.create(gran);
        User expected = gran;
        assertEquals(expected, actual, "Соблюдены не все граничные условия");
    }

    @Test
    void test3_createUserNullName() {
        User actual = controller.create(nameNull);
        User expected = nameNull;
        assertEquals(expected, actual, "Пользователь с пустым именем не создан!");
    }

    @Test
    void test4_createUserLoginNull() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(loginNull);
                    }
                });
        assertEquals("Пользователь не создан!", exception.getMessage()
                , "Не должен обрабатывать пустой логин");
    }

    @Test
    void test5_createUserFutureBirthday() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(futureBirthday);
                    }
                });
        assertEquals("Пользователь не создан!", exception.getMessage()
                , "Не должен обрабатывать дни рождения позже сегодняшнего дня");
    }

    @Test
    void test6_createUserEmailError() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(emailError);
                    }
                });
        assertEquals("Пользователь не создан!", exception.getMessage()
                , "Не должен обрабатывать не корректно введенную почту");
    }

    //Обновление пользователей
    @Test
    void test7_updateUserGran() {
        gran.setId(1);
        User actual = controller.update(gran);
        User expected = gran;
        List<User> actualList = new ArrayList<>(controller.getUsers());
        List<User> expectedList = new ArrayList<>();
        expectedList.add(gran);
        assertEquals(expected, actual, "При обновлении пользователя соблюдены не все граничные условия");
        assertEquals(expectedList, actualList, "Проблема при добавлении в лист");
    }

    @Test
    void test8_updateUserNullName() {
        nameNull.setId(1);
        User actual = controller.update(nameNull);
        nameNull.setName("tests");
        User expected = nameNull;
        List<User> actualList = new ArrayList<>(controller.getUsers());
        List<User> expectedList = new ArrayList<>();
        expectedList.add(nameNull);
        assertEquals(expected, actual, "Пользователь с пустым именем не создан!");
        assertEquals(expectedList, actualList, "Проблема при добавлении в лист");
    }

    @Test
    void test9_updateUserLoginNull() {
        loginNull.setId(1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(loginNull);
                    }
                });
        assertEquals("Пользователь не обновлен!", exception.getMessage()
                , "Не должен обрабатывать пустой логин");
    }

    @Test
    void test10_updateUserFutureBirthday() {
        futureBirthday.setId(1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(futureBirthday);
                    }
                });
        assertEquals("Пользователь не обновлен!", exception.getMessage()
                , "Не должен обрабатывать дни рождения позже сегодняшнего дня");
    }

    @Test
    void test11_updateUserEmailError() {
        emailError.setId(1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(emailError);
                    }
                });
        assertEquals("Пользователь не обновлен!", exception.getMessage()
                , "Не должен обрабатывать не корректно введенную почту");
    }

    @Test
    void test12_getAllUsers() {
        controller.create(gran);
        List<User> actual = new ArrayList<>(controller.getUsers());
        List<User> expected = new ArrayList<>();
        expected.add(user);
        expected.add(gran);
        assertEquals(expected, actual);
    }
}