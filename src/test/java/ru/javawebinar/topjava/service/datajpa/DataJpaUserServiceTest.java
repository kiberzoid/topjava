package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void testGetWithMeals() throws Exception {
        User adminWithMeals = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(adminWithMeals, admin);
        MEAL_MATCHER.assertMatch(adminWithMeals.getMeals(), MealTestData.adminMeal2, MealTestData.adminMeal1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithMealsNotFound() throws Exception {
        service.getWithMeals(1);
    }

    @Test
    public void testGetNoMealsExists() throws Exception {
        User hungryUser = service.getWithMeals(HUNGRY_USER_ID);
        USER_MATCHER.assertMatch(hungryUser, hungry);
        MEAL_MATCHER.assertMatch(hungryUser.getMeals());
    }
}
