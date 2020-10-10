package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    public final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("Create meal for user {}", authUserId());
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete meal {} for user {}", id, authUserId());
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("Get meal {} for user {}", id, authUserId());
        return service.get(id, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("Get meals for user {}", authUserId());
        return service.getAll(authUserId(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal, int id) {
        log.info("Update meal {} for user {}", meal.getId(), authUserId());
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public List<MealTo> getAllFiltered(LocalDate dFrom, LocalDate dTo, LocalTime tFrom, LocalTime tTo) {
        log.info("Get filtered meals for user {}", authUserId());
        return service.getAllFiltered(authUserId(), dFrom, dTo, tFrom, tTo, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}