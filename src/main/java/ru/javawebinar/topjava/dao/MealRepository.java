package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    public List<Meal> getMeals();
    public Meal getMeal(int id);
    public Meal createMeal(LocalDateTime dateTime, String description, int calories);
    public Meal createOrUpdateMeal(Meal meal);
    public int deleteMeal(int id);
}

