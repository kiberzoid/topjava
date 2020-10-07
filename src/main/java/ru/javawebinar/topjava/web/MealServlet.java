package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.InMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private static final String insertOrDelete = "/meal.jsp";
    private static final String listMeals = "/meals.jsp";
    private static final int caloriesPerDay = 2000;
    private MealRepository repo;

    @Override
    public void init() {
        this.repo = new InMemoryMealRepository();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("processing POST request");
        String id = request.getParameter("id");
        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("dateTime"));
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id != null && !id.isEmpty()) {
            logger.info("update meal");
            int mealId = parseIdParam(id);
            Meal meal = new Meal(mealId, ldt, desc, calories);
            repo.update(meal);
        } else {
            logger.info("create meal");
            repo.create(new Meal(0, ldt, desc, calories));
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("processing GET request");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        switch (action == null ? "" : action) {
            case "delete": {
                if (id == null) {
                    response.sendRedirect("meals");
                    break;
                }
                int mealId = parseIdParam(id);
                logger.info("try to delete meal with id: {}", mealId);
                repo.delete(mealId);
                response.sendRedirect("meals");
                break;
            }
            case "edit": {
                if (id == null) {
                    response.sendRedirect("meals");
                    break;
                }
                int mealId = parseIdParam(id);
                logger.info("try to edit meal with id: {}", mealId);
                Meal meal = repo.get(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(insertOrDelete).forward(request, response);
                break;
            }
            case "create": {
                logger.info("try to create new meal");
                request.getRequestDispatcher(insertOrDelete).forward(request, response);
            }
            default: {
                logger.info("try to view meals");
                request.setAttribute("meals",
                        MealsUtil.filteredByStreams(repo.getMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
                request.getRequestDispatcher(listMeals).forward(request, response);
            }
        }
    }

    private int parseIdParam(String id) {
        return Integer.parseInt(id);
    }
}
