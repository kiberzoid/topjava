package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.SimpleMealRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private static final MealRepository repo = new SimpleMealRepositoryImpl();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("processing POST request");
        LocalDateTime ldt = LocalDateTime.from(formatter.parse(request.getParameter("dateTime")));
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int mealId = 0;
        if (request.getParameter("id").equals("")) {
        } else {
            mealId = Integer.parseInt(request.getParameter("id"));
        }
        logger.info("create or update meal");
        Meal meal = new Meal(ldt, desc, calories, mealId);
        repo.createOrUpdateMeal(meal);
        request.setAttribute("meals", MealsUtil.createMealToList(repo.getMeals(), 2000));
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("processing GET request");
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            logger.info("try to delete meal with id: " + mealId);
            repo.deleteMeal(mealId);
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.createMealToList(repo.getMeals(), 2000));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            logger.info("try to edit meal with id: " + mealId);
            Meal meal = repo.getMeal(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("viewMeals")) {
            logger.info("try to view meals");
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.createMealToList(repo.getMeals(), 2000));
        } else {
            logger.info("try to create new meal");
            forward = INSERT_OR_EDIT;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }
}
