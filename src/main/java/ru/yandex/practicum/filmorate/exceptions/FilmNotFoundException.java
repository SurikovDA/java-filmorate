package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(final String message) {
        super(message);
    }

    public FilmNotFoundException() {
        super();
    }
}
