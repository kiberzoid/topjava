package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAll(int userId, int caloriesPerDate) {
        return MealsUtil.getTos(repository.getAll(userId), caloriesPerDate);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<MealTo> getAllFiltered(int userId, LocalDate dFrom, LocalDate dTo, LocalTime tFrom, LocalTime tTo, int caloriesPerDate) {
        List<MealTo> mealTos = MealsUtil.getTos(repository.getAll(userId), caloriesPerDate);
        return mealTos.stream()
                .filter(mealTo -> (DateTimeUtil.isBetweenOpen(mealTo.getDate(), dFrom, dTo)))
                .filter(mealTo -> (DateTimeUtil.isBetweenHalfOpen(mealTo.getTime(), tFrom, tTo)))
                .sorted(Comparator.comparing(MealTo::getDate).reversed().thenComparing(MealTo::getTime).reversed())
                .collect(Collectors.toList());
    }
}