package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Имя не может быть пустым!")
    private String name;
    @Length(max = 200, message = "Описание не должно превышать 200 символов!")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной!")
    private Integer duration;
}
