package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, Integer userId);

    // false if not found
    boolean delete(int id, Integer userId);

    // null if not found
    Meal get(int id, Integer userId);

    List<Meal> getAll(Integer userId);
    List<MealTo> getAllFiltered(int userId, LocalDate dFrom, LocalDate dTo, LocalTime tFrom, LocalTime tTo, int caloriesPerDate);
}
