package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getMeals();

    Meal get(int id);

    Meal create(Meal meal);

    Meal update(Meal meal);

    boolean delete(int id);
}

