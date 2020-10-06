package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    List<Meal> getMeals();

    Meal getMeal(int id);

    Meal createMeal(LocalDateTime dateTime, String description, int calories);

    Meal updateMeal(Meal meal);

    int deleteMeal(int id);
}

