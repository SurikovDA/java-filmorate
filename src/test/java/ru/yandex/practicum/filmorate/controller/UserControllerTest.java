package ru.yandex.practicum.filmorate.controller;

/*
class UserControllerTest {
    UserController controller;
    User user = new User(1, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(1995, 12, 25));
    User existingId = new User(1, "yandex@mail.ru", "tests", "",
            LocalDate.of(2023, 3, 25));
    User nulId = new User(0, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(1995, 12, 25));
    User updateUser = new User(1, "yandex@mail.ru", "tests", "test4",
            LocalDate.of(1999, 10, 2));
    User futureBirthday = new User(0, "yandex@mail.ru", "tests", "test1",
            LocalDate.of(2223, 4, 25));

    @BeforeEach
    void createUserController() {
        controller = new UserController();
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
        assertEquals("Пользователь не найден!", exception.getMessage(),
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