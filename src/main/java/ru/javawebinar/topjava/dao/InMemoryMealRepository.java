package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private AtomicInteger count = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    public InMemoryMealRepository() {

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
    public Meal createMeal(Meal meal) {
        int id = count.getAndIncrement();
        Meal newMeal = new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        meals.put(id, newMeal);
        return newMeal;
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return meals.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public boolean deleteMeal(int id) {
        return meals.remove(id) != null;
    }

    private void createTestMeals() {
        meals.put(1, new Meal(count.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(count.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(3, new Meal(count.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(4, new Meal(count.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(5, new Meal(count.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(6, new Meal(count.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(7, new Meal(count.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
}
