package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.MemoryInMealRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealServlet.class);
    private final MealRepository repo = new MemoryInMealRepositoryImpl();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("processing POST request");
        LocalDateTime ldt = LocalDateTime.from(formatter.parse(request.getParameter("dateTime")));
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            LOGGER.info("update meal");
            int mealId = Integer.parseInt(request.getParameter("id"));
            Meal meal = new Meal(mealId, ldt, desc, calories);
            repo.updateMeal(meal);
        } else {
            LOGGER.info("create meal");
            repo.createMeal(ldt, desc, calories);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("processing GET request");
        String action = request.getParameter("action");
        if (action == null) {
            LOGGER.info("try to view meals");
            request.setAttribute("meals",
                    MealsUtil.filteredByStreams(repo.getMeals(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
            request.getRequestDispatcher(LIST_MEAL).forward(request, response);
            return;
        }
        switch (action) {
            case "delete": {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                LOGGER.info("try to delete meal with id: " + mealId);
                repo.deleteMeal(mealId);
                response.sendRedirect("meals");
                break;
            }
            case "edit": {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                LOGGER.info("try to edit meal with id: " + mealId);
                Meal meal = repo.getMeal(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
                break;
            }
            case "create": {
                LOGGER.info("try to create new meal");
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
            }
            default: {
                LOGGER.info("try to view meals");
                request.setAttribute("meals",
                        MealsUtil.filteredByStreams(repo.getMeals(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
                request.getRequestDispatcher(LIST_MEAL).forward(request, response);
            }
        }
    }
}
