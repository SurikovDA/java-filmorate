package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Mpa {
    @PositiveOrZero
    private int id;
    @NotBlank(message = "Название возрастного ограничения не может быть пустым!")
    private String name;
    @NotBlank(message = "Описание возрастного ограничения не может быть пустым!")
    private String description;

    public Mpa(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
