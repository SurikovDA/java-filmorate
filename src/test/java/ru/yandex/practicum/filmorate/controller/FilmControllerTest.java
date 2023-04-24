package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController controller;
    Film film = new Film(1, "pirates", "adventures",
            LocalDate.of(2003, 7, 9), 120);
    Film gran = new Film(0, "pirates2", "«Пираты Карибского моря» (англ. Pirates of " +
            "the Caribbean) — серия приключенческих фильмов о пиратах в Карибском море, режиссёрами которых выступили" +
            " Гор Вербински (1—3 части), Роб Маршалл (4-я часть), ",
            LocalDate.of(1895, 12, 28), 1);
    Film nul = new Film();
    Film nullName = new Film(0, "", "adventures",
            LocalDate.of(2003, 7, 9), 120);
    Film descriptions201 = new Film(0, "pirates2", "«Пираты Карибского моря» (англ. Pirates of " +
            "the Caribbean) — серия приключенческих фильмов о пиратах в Карибском море, режиссёрами которых выступили" +
            " Гор Вербински (1—3 части), Роб Маршалл (4-я часть), Э",
            LocalDate.of(2005, 12, 25), 120);
    Film descriptions200 = new Film(0, "pirates2", "«Пираты Карибского моря» (англ. Pirates of " +
            "the Caribbean) — серия приключенческих фильмов о пиратах в Карибском море, режиссёрами которых выступили" +
            " Гор Вербински (1—3 части), Роб Маршалл (4-я часть), ",
            LocalDate.of(2005, 12, 25), 120);
    Film oldDate = new Film(1, "pirates2", "adventures",
            LocalDate.of(1895, 12, 27), 120);
    Film durationNegative = new Film(0, "pirates2", "adventures",
            LocalDate.of(2005, 12, 25), -1);

    @BeforeEach
    void createController() {
        controller = new FilmController();
        controller.create(film);
    }

    @Test
    void test1_generateIdFilm() {
        int factId = controller.generateIdFilm(film);
        int expectedId = 2;
        assertEquals(expectedId, factId, "Не верно сгенерирован id");
    }

    @Test
    void test2_createFilmsGran() {
        Film actual = controller.create(gran);
        Film expected = gran;
        assertEquals(expected, actual, "Соблюдены не все граничные условия");
    }

    @Test
    void test3_createFilmsNullName() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(nullName);
                    }
                });
        assertEquals("Новый фильм не создан!", exception.getMessage(), "Не обрабатывает пустое " +
                "название фильма");
    }

    @Test
    void test4_createFilmsDescriptions201() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(descriptions201);
                    }
                });
        assertEquals("Новый фильм не создан!", exception.getMessage(), "Не должен обрабатывать " +
                "описание фильма больше 200 символов");
    }

    @Test
    void test5_createFilmsOldDate() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(oldDate);
                    }
                });
        assertEquals("Новый фильм не создан!", exception.getMessage(), "Не должен обрабатывать фильмы" +
                " старше 28.12.1895");
    }

    @Test
    void test6_createFilmsOldDate() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(durationNegative);
                    }
                });
        assertEquals("Новый фильм не создан!", exception.getMessage(), "Не должен обрабатывать фильмы" +
                " старше 28.12.1895");
    }

    //Обновление фильмов
    @Test
    void test7_updateFilmsGran() {
        gran.setId(1);
        Film actual = controller.update(gran);
        Film expected = gran;
        List<Film> actualList = new ArrayList<>(controller.getFilms());
        List<Film> expectedList = new ArrayList<>();
        expectedList.add(gran);
        assertEquals(expected, actual, "При обновлении фильма соблюдены не все граничные условия");
        assertEquals(expectedList, actualList, "Проблема при добавлении в лист");
    }

    @Test
    void test8_updateFilmsNullName() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(nullName);
                    }
                });
        assertEquals("Фильм не обновлен!", exception.getMessage(), "Не обрабатывает пустое " +
                "название фильма");
    }

    @Test
    void test9_updateFilmsDescriptions201() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(descriptions201);
                    }
                });
        assertEquals("Фильм не обновлен!", exception.getMessage(), "Не должен обрабатывать " +
                "описание фильма больше 200 символов");
    }

    @Test
    void test10_updateFilmsOldDate() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(oldDate);
                    }
                });
        assertEquals("Фильм не обновлен!", exception.getMessage(), "Не должен обрабатывать фильмы" +
                " старше 28.12.1895");
    }

    @Test
    void test11_updateFilmsOldDate() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(durationNegative);
                    }
                });
        assertEquals("Фильм не обновлен!", exception.getMessage(), "Не должен обновлять фильм с " +
                "отрицательной продолжительностью");
    }

    @Test
    void test12_getFilms() {
        controller.create(gran);
        List<Film> actual = new ArrayList<>(controller.getFilms());
        List<Film> expected = new ArrayList<>();
        expected.add(film);
        expected.add(gran);
        assertEquals(expected, actual);
    }
}
