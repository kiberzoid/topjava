package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SimpleMealRepositoryImpl implements MealRepository {
    private static AtomicInteger countMeals = new AtomicInteger(8);
    private static final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    public SimpleMealRepositoryImpl() {
        meals.put(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 1));
        meals.put(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 2));
        meals.put(3, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 3));
        meals.put(4, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 4));
        meals.put(5, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 5));
        meals.put(6, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 6));
        meals.put(7, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 7));
    }

    @Override
    public List<Meal> getMeals() {
        return meals.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public Meal getMeal(int id) {
        return meals.get(id);
    }

    @Override
    public Meal createMeal(LocalDateTime dateTime, String description, int calories) {
        int id = countMeals.getAndIncrement();
        Meal meal = new Meal(dateTime, description, calories, id);
        meals.putIfAbsent(id, meal);
        return meal;
    }

    @Override
    public Meal createOrUpdateMeal(Meal meal) {
        if (!meals.containsKey(meal.getId())) {
            int id = countMeals.getAndIncrement();
            Meal newMeal = new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), id);
            meals.put(id, newMeal);
            return newMeal;
        } else {
            meals.put(meal.getId(), meal);
            return meal;
        }
    }

    @Override
    public int deleteMeal(int id) {
        if (meals.remove(id) == null) return 0;
        return 1;
    }
}
