package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    @Email(message = "Не верный формат email!")
    @NotBlank(message = "Введите email!")
    private String email;
    private String login;
    private String name = null;
    private LocalDate birthday;
}