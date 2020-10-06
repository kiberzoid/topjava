package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryInMealRepositoryImpl implements MealRepository {
    private AtomicInteger count = new AtomicInteger(8);
    private final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MemoryInMealRepositoryImpl() {
        meals.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(7, new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> getMeals() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getMeal(int id) {
        return meals.get(id);
    }

    @Override
    public Meal createMeal(LocalDateTime dateTime, String description, int calories) {
        int id = count.getAndIncrement();
        Meal newMeal = new Meal(id, dateTime, description, calories);
        meals.put(id, newMeal);
        return newMeal;
    }

    @Override
    public Meal updateMeal(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public int deleteMeal(int id) {
        return meals.remove(id) == null ? 0 : 1;
    }
}
