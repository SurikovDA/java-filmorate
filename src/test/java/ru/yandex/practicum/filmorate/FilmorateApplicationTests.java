package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

    private final UserService userService;
    private final UserController userController;
    private final FilmService filmService;
    private final FilmController filmController;

    //Создание пользователей
    Set<Long> friends = new HashSet<>();
    User user = new User(0, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(1995, 12, 25), friends);

    User existingId = new User(1, "yandex@mail.ru", "tests", "",
            LocalDate.of(2023, 3, 25), friends);
    User nulId = new User(0, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(1995, 12, 25), friends);
    User updateUser = new User(1, "yandex@mail.ru", "tests", "test4",
            LocalDate.of(1999, 10, 2), friends);
    User futureBirthday = new User(0, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(2223, 4, 25), friends);

    //Создание фильмов
    Set<Long> likes = new HashSet<>();
    List<Genre> genres = new ArrayList<>();
    Mpa mpa = new Mpa(1, "G");
    Film film = new Film(0, "pirates", "adventures",
            LocalDate.of(2003, 7, 9), 120, likes, genres, mpa);
    Film createFilm = new Film(0, "pirates", "adventures",
            LocalDate.of(2003, 7, 9), 120, likes, genres, mpa);
    Film existId = new Film(1, "pirates2", "«Пираты Карибского моря» (англ. Pirates of " +
            "the Caribbean) — серия приключенческих фильмов о пиратах в Карибском море, режиссёрами которых выступили" +
            " Гор Вербински (1—3 части), Роб Маршалл (4-я часть), ",
            LocalDate.of(1895, 12, 28), 1, likes, genres, mpa);
    Film oldDate = new Film(0, "pirates2", "adventures",
            LocalDate.of(1895, 12, 27), 120, likes, genres, mpa);

    //Тесты пользователей
    @BeforeEach
    void createUserController() {
        userController.create(user);
    }

    @Test
    public void test1_FindUserById() {
        Optional<User> userOptional = Optional.of(userService.getUserById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    @Test
    void test2_createUserNulId() {
        User actual = userController.create(nulId);
        User expected = nulId;
        expected.setId(2);
        assertEquals(expected, actual, "Не удается создать пользователя");
    }

    @Test
    void test3_createUserExistingId() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        userService.create(existingId);
                    }
                });
        assertEquals("Пользователь с таким id уже есть в базе", exception.getMessage(),
                "Создает пользователей с уже существующим id");
    }

    @Test
    void test4_createUserFutureBirthday() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        userController.create(futureBirthday);
                    }
                });
        assertEquals("Укажите корректную дату рождения!", exception.getMessage(),
                "Создает пользователей с днем рождения позже сегодняшнего дня");
    }

    //Обновление пользователей
    @Test
    void test5_updateUser() {
        User actual = userController.update(updateUser);
        User expected = updateUser;
        assertEquals(expected, actual, "Не удается обновить пользователя");
    }

    @Test
    void test6_updateUserNulId() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        userController.update(nulId);
                    }
                });
        assertEquals("id пользователя не может быть равен 0!", exception.getMessage(),
                "Обновляет пользователей без указанного id");
    }

    @Test
    void test7_updateUserFutureBirthday() {
        futureBirthday.setId(1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        userController.update(futureBirthday);
                    }
                });
        assertEquals("Укажите корректную дату рождения!", exception.getMessage(),
                "Обновляет пользователей с днем рождения позже сегодняшнего дня");
    }

    @Test
    void test8_getAllUsers() {
        existingId.setId(0);
        userController.create(existingId);
        List<User> actual = new ArrayList<>(userController.getUsers());
        int expected = 12;
        assertEquals(expected, actual.size());
    }


    //Тесты фильмов
    @BeforeEach
    void createController() {
        filmController.create(film);
    }

    @Test
    void test9_createFilmsExistingId() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        filmController.create(existId);
                    }
                });
        assertEquals("Фильм с указанным id уже существует!", exception.getMessage(),
                "Не должен обрабатывать фильмы с уже существующим id");
    }

    @Test
    void test10_createFilm() {
        Film actual = filmController.create(createFilm);
        Film expected = createFilm;
        expected.setId(2);
        assertEquals(expected, actual, "Не удается создать фильм");
    }

    @Test
    void test11_createFilmsOldDate() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        filmController.create(oldDate);
                    }
                });
        assertEquals("Фильм не создан. Дата выпуска фильма не может быть раньше даты создания кино!", exception.getMessage(), "Не должен обрабатывать фильмы" +
                " старше 28.12.1895");
    }

    //Обновление фильмов
    @Test
    void test12_updateFilm() {
        Film actual = filmController.update(existId);
        Film expected = existId;
        assertEquals(expected, actual, "Не удается обновить фильм");
    }

    @Test
    void test13_updateFilmsExistingId() {
        existId.setId(1);
        Film actual = filmController.update(existId);
        Film expected = existId;
        assertEquals(expected, actual, "При обновлении фильма соблюдены не все граничные условия");
    }

    @Test
    void test14_updateFilmsOldDate() {
        oldDate.setId(1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        filmController.update(oldDate);
                    }
                });
        assertEquals("Фильм не обновлен. Дата выпуска фильма не может быть раньше даты создания кино!",
                exception.getMessage(), "Не должен обрабатывать фильмы старше 28.12.1895");
    }

    @Test
    void test15_getFilms() {
        List<Film> actual = new ArrayList<>(filmController.getFilms());
        int expected = 10;
        assertEquals(expected, actual.size());
    }

    //Проверка дополнительного функционала

}

