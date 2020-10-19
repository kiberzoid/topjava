package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int BREAKFAST_ADMIN_ID = START_SEQ + 2;
    public static final int SUPPER_ADMIN_ID = START_SEQ + 3;

    public static final int BREAKFAST_USER_ID = START_SEQ + 4;
    public static final int DINNER_USER_ID = START_SEQ + 5;
    public static final int SUPPER_USER_ID = START_SEQ + 6;

    public static final int NOT_FOUND_MEAL = 30;

    public static final Meal breakfastUser =
            new Meal(BREAKFAST_USER_ID, LocalDateTime.of(2020, Month.SEPTEMBER, 30, 8, 0), "Завтрак", 1000);
    public static final Meal dinnerUser =
            new Meal(DINNER_USER_ID, LocalDateTime.of(2020, Month.SEPTEMBER, 30, 13, 0), "Обед", 500);
    public static final Meal supperUser =
            new Meal(SUPPER_USER_ID, LocalDateTime.of(2020, Month.SEPTEMBER, 30, 20, 0), "Ужин", 2500);
    public static final Meal breakfastAdmin =
            new Meal(BREAKFAST_ADMIN_ID, LocalDateTime.of(2020, Month.JULY, 9, 6, 0), "Завтрак", 300);
    public static final Meal supperAdmin =
            new Meal(SUPPER_ADMIN_ID, LocalDateTime.of(2020, Month.JULY, 9, 20, 0), "Ужин", 1000);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JULY, 9, 13, 0), "Обед", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(breakfastAdmin);
        updated.setDateTime(LocalDateTime.of(2020, Month.JUNE, 25, 7, 43));
        updated.setDescription("Updated Admin Завтрак");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
