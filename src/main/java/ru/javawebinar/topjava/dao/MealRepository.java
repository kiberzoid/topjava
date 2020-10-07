package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    List<Meal> getMeals();

    Meal getMeal(int id);

    Meal createMeal(Meal meal);

    Meal updateMeal(Meal meal);

    boolean deleteMeal(int id);
}

