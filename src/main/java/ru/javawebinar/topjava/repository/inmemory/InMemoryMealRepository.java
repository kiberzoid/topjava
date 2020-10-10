package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        meal.setUserId(userId);
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) ->
                oldMeal.getUserId().equals(userId) ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        return repository.remove(id, get(id, userId));
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal meal = repository.getOrDefault(id, new Meal(null, null, 0));
        return meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getAllFiltered(int userId, LocalDate dFrom, LocalDate dTo, LocalTime tFrom, LocalTime tTo, int caloriesPerDate) {
        List<MealTo> meals = MealsUtil.getTos(getAll(userId), caloriesPerDate);
        return meals.stream()
                .filter(mealTo -> (DateTimeUtil.isBetweenDate(mealTo.getDate(), dFrom, dTo)))
                .filter(mealTo -> (DateTimeUtil.isBetweenTime(mealTo.getTime(), tFrom, tTo)))
                .sorted(Comparator.comparing(MealTo::getDate).reversed())
                .collect(Collectors.toList());
    }
}

