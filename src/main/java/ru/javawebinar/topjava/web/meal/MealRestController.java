package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int authUserId = authUserId();
        log.info("Create meal for user {}", authUserId);
        checkNew(meal);
        return service.create(meal, authUserId);
    }

    public void delete(int id) {
        int authUserId = authUserId();
        log.info("delete meal {} for user {}", id, authUserId);
        service.delete(id, authUserId);
    }

    public Meal get(int id) {
        int authUserId = authUserId();
        log.info("Get meal {} for user {}", id, authUserId);
        return service.get(id, authUserId);
    }

    public List<MealTo> getAll() {
        int authUserId = authUserId();
        log.info("Get meals for user {}", authUserId);
        return service.getAll(authUserId, SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int id) {
        int authUserId = authUserId();
        log.info("Update meal {} for user {}", meal.getId(), authUserId);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId);
    }

    public List<MealTo> getAllFiltered(LocalDate dFrom, LocalDate dTo, LocalTime tFrom, LocalTime tTo) {
        int authUserId = authUserId();
        log.info("Get filtered meals for user {}", authUserId);
        LocalDate dateFrom = dFrom == null ? LocalDate.MIN : dFrom;
        LocalDate dateTo = dTo == null ? LocalDate.MAX : dTo;
        LocalTime timeFrom = tFrom == null ? LocalTime.MIN : tFrom;
        LocalTime timeTo = tTo == null ? LocalTime.MAX : tTo;
        return service.getAllFiltered(authUserId,
                dateFrom, dateTo, timeFrom, timeTo, SecurityUtil.authUserCaloriesPerDay());
    }
}