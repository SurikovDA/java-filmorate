package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    @PositiveOrZero
    private long id;
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
    Set<Long> likes = new HashSet<>();
    int rating = 0;
    List<Genre> genres = new ArrayList<>();
    Mpa mpa = new Mpa();
}
