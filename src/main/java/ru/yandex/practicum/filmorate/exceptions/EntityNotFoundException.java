package ru.yandex.practicum.filmorate.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(final String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super();
    }
}
