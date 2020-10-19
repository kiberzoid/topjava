package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int ADMIN_BREAKFAST_ID = START_SEQ + 2;
    public static final int ADMIN_SUPPER_ID = START_SEQ + 3;

    public static final int USER_BREAKFAST_3009_ID = START_SEQ + 4;
    public static final int USER_DINNER_3009_ID = START_SEQ + 5;
    public static final int USER_SUPPER_3009_ID = START_SEQ + 6;
    public static final int USER_BREAKFAST_0110_ID = START_SEQ + 7;
    public static final int USER_DINNER_0110_ID = START_SEQ + 8;
    public static final int USER_SUPPER_0110_ID = START_SEQ + 9;
    public static final int USER_BREAKFAST_0210_ID = START_SEQ + 10;
    public static final int USER_DINNER_0210_ID = START_SEQ + 11;
    public static final int USER_SUPPER_0210_ID = START_SEQ + 12;
    public static final int USER_BREAKFAST_0610_ID = START_SEQ + 13;
    public static final int USER_DINNER_0610_ID = START_SEQ + 14;
    public static final int USER_SUPPER_0610_ID = START_SEQ + 15;

    public static final int NOT_FOUND_MEAL = 30;

    public static final Meal adminBreakfast0906 =
            new Meal(ADMIN_BREAKFAST_ID, LocalDateTime.of(2020, Month.JULY, 9, 6, 0), "Админ завтрак", 300);
    public static final Meal adminSupper0906 =
            new Meal(ADMIN_SUPPER_ID, LocalDateTime.of(2020, Month.JULY, 9, 20, 0), "Админ ужин", 1000);

    public static final Meal userBreakfast3009 =
            new Meal(USER_BREAKFAST_3009_ID, LocalDateTime.of(2020, Month.SEPTEMBER, 30, 8, 0), "Завтрак", 1000);
    public static final Meal userDinner3009 =
            new Meal(USER_DINNER_3009_ID, LocalDateTime.of(2020, Month.SEPTEMBER, 30, 13, 0), "Обед", 500);
    public static final Meal userSupper3009 =
            new Meal(USER_SUPPER_3009_ID, LocalDateTime.of(2020, Month.SEPTEMBER, 30, 20, 0), "Ужин", 2500);
    public static final Meal userBreakfast0110 =
            new Meal(USER_BREAKFAST_0110_ID, LocalDateTime.of(2020, Month.OCTOBER, 1, 7, 45), "Завтрак", 950);
    public static final Meal userDinner0110 =
            new Meal(USER_DINNER_0110_ID, LocalDateTime.of(2020, Month.OCTOBER, 1, 14, 0), "Обед", 400);
    public static final Meal userSupper0110 =
            new Meal(USER_SUPPER_0110_ID, LocalDateTime.of(2020, Month.OCTOBER, 1, 19, 30), "Ужин", 400);
    public static final Meal userBreakfast0210 =
            new Meal(USER_BREAKFAST_0210_ID, LocalDateTime.of(2020, Month.OCTOBER, 2, 7, 55), "Завтрак", 200);
    public static final Meal userDinner0210 =
            new Meal(USER_DINNER_0210_ID, LocalDateTime.of(2020, Month.OCTOBER, 2, 12, 50), "Обед", 500);
    public static final Meal userSupper0210 =
            new Meal(USER_SUPPER_0210_ID, LocalDateTime.of(2020, Month.OCTOBER, 2, 20, 9), "Ужин", 1300);
    public static final Meal userBreakfast0610 =
            new Meal(USER_BREAKFAST_0610_ID, LocalDateTime.of(2020, Month.OCTOBER, 6, 8, 4), "Завтрак", 400);
    public static final Meal userDinner0610 =
            new Meal(USER_DINNER_0610_ID, LocalDateTime.of(2020, Month.OCTOBER, 6, 13, 20), "Обед", 600);
    public static final Meal userSupper0610 =
            new Meal(USER_SUPPER_0610_ID, LocalDateTime.of(2020, Month.OCTOBER, 6, 19, 59), "Ужин", 900);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JULY, 9, 13, 0), "Обед", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(adminBreakfast0906);
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
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
