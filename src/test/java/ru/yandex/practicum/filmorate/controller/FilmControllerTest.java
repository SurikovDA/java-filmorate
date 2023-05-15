package ru.yandex.practicum.filmorate.controller;

/*
class FilmControllerTest {
    FilmController controller;
    Film film = new Film(0, "pirates", "adventures",
            LocalDate.of(2003, 7, 9), 120);
    Film createFilm = new Film(0, "pirates", "adventures",
            LocalDate.of(2003, 7, 9), 120);
    Film existingId = new Film(1, "pirates2", "«Пираты Карибского моря» (англ. Pirates of " +
            "the Caribbean) — серия приключенческих фильмов о пиратах в Карибском море, режиссёрами которых выступили" +
            " Гор Вербински (1—3 части), Роб Маршалл (4-я часть), ",
            LocalDate.of(1895, 12, 28), 1);
    Film oldDate = new Film(0, "pirates2", "adventures",
            LocalDate.of(1895, 12, 27), 120);

    @BeforeEach
    void createController() {
        //controller = new FilmController();
        controller.create(film);
    }


    @Test
    void test1_createFilmsExistingId() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(existingId);
                    }
                });
        assertEquals("Фильм с указанным id уже существует!", exception.getMessage(),
                "Не должен обрабатывать фильмы с уже существующим id");
    }

    @Test
    void test2_createFilm() {
        Film actual = controller.create(createFilm);
        Film expected = createFilm;
        expected.setId(2);
        assertEquals(expected, actual, "Не удается создать фильм");
    }

    @Test
    void test3_createFilmsOldDate() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.create(oldDate);
                    }
                });
        assertEquals("Фильм не создан. Дата выпуска фильма не может быть раньше даты создания кино!", exception.getMessage(), "Не должен обрабатывать фильмы" +
                " старше 28.12.1895");
    }

    //Обновление фильмов
    @Test
    void test4_updateFilm() {
        Film actual = controller.update(existingId);
        Film expected = existingId;
        assertEquals(expected, actual, "Не удается обновить фильм");
    }

    @Test
    void test5_updateFilmsExistingId() {
        existingId.setId(1);
        Film actual = controller.update(existingId);
        Film expected = existingId;
        List<Film> actualList = new ArrayList<>(controller.getFilms());
        List<Film> expectedList = new ArrayList<>();
        expectedList.add(existingId);
        assertEquals(expected, actual, "При обновлении фильма соблюдены не все граничные условия");
        assertEquals(expectedList, actualList, "Проблема при добавлении в лист");
    }

    @Test
    void test6_updateFilmsOldDate() {
        oldDate.setId(1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        controller.update(oldDate);
                    }
                });
        assertEquals("Фильм не обновлен. Дата выпуска фильма не может быть раньше даты создания кино!",
                exception.getMessage(), "Не должен обрабатывать фильмы старше 28.12.1895");
    }

    @Test
    void test7_getFilms() {
        existingId.setId(0);
        controller.create(existingId);
        List<Film> actual = new ArrayList<>(controller.getFilms());
        List<Film> expected = new ArrayList<>();
        expected.add(film);
        expected.add(existingId);
        assertEquals(expected, actual);
    }
}
 */