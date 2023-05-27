package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Genre {
    @PositiveOrZero
    private long id;
    @NotBlank(message = "Название жанра не может быть пустым!")
    private String name;
}
