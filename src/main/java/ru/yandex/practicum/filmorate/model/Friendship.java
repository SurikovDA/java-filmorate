package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Friendship {
    @PositiveOrZero
    private long userId;
    @PositiveOrZero
    private long friendId;
}
