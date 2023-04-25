package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не может быть пустым!")
    @NotNull(message = "Задайте название фильма!")
    private String name;
    @NotBlank(message = "Описание фильма не может быть пустым")
    @Length(max = 200, message = "Описание не должно превышать 200 символов!")
    private String description;
    @NotNull(message = "Дата выпуска не может быть пустой")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной!")
    private Integer duration;
}
