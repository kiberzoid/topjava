package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal actual = service.get(USER_BREAKFAST_3009_ID, USER_ID);
        assertMatch(actual, userBreakfast3009);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void getForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.get(USER_BREAKFAST_3009_ID, ADMIN_ID));

    }

    @Test
    public void delete() {
        service.delete(ADMIN_SUPPER_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_SUPPER_ID, ADMIN_ID));
    }

    @Test
    public void deleteForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_BREAKFAST_3009_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFoundMealForUser() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, Month.MAY, 31),
                LocalDate.of(2020, Month.AUGUST, 30), ADMIN_ID);
        assertMatch(meals, adminSupper0906, adminBreakfast0906);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, userSupper0610, userDinner0610, userBreakfast0610,
                userSupper0210, userDinner0210, userBreakfast0210,
                userSupper0110, userDinner0110, userBreakfast0110,
                userSupper3009, userDinner3009, userBreakfast3009);
    }

    @Test
    public void update() {
        Meal meal = getUpdated();
        service.update(meal, ADMIN_ID);
        assertMatch(service.get(meal.getId(), ADMIN_ID), meal);
    }

    @Test
    public void updateForeignMeal() {
        Meal meal = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(meal, USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal createdMeal = service.create(newMeal, ADMIN_ID);
        Integer newId = createdMeal.getId();
        newMeal.setId(newId);
        assertMatch(createdMeal, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), createdMeal);
    }

    @Test
    public void duplicateUserDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(userDinner3009.getDateTime(), "Обед", 500), USER_ID));
    }
}