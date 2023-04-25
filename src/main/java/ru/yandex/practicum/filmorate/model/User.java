package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    @Email(message = "Не верный формат email!")
    @NotBlank(message = "Введите email!")
    private String email;
    @NotBlank(message = "Введите логин! Логин не может быть пустым")
    private String login;
    private String name;
    @NotNull(message = "Дата рождения не может быть пустой!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}