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
    private static final Logger Logger = LoggerFactory.getLogger(MealServlet.class);
    private static final String InsertOrDelete = "/meal.jsp";
    private static final String ListMeals = "/meals.jsp";
    private static final int CaloriesPerDay = 2000;
    private MealRepository repo;

    @Override
    public void init() throws ServletException {
        super.init();
        this.repo = new InMemoryMealRepository();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger.info("processing POST request");
        String id = request.getParameter("id");
        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("dateTime"));
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id != null && !id.equals("")) {
            Logger.info("update meal");
            int mealId = parseIdParam(id);
            Meal meal = new Meal(mealId, ldt, desc, calories);
            repo.updateMeal(meal);
        } else {
            Logger.info("create meal");
            repo.createMeal(new Meal(0,ldt, desc, calories));
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger.info("processing GET request");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        switch (action == null ? "" : action) {
            case "delete": {
                if (id == null) {
                    response.sendRedirect("meals");
                    break;
                }
                int mealId = parseIdParam(id);
                Logger.info("try to delete meal with id: " + mealId);
                repo.deleteMeal(mealId);
                response.sendRedirect("meals");
                break;
            }
            case "edit": {
                if (id == null) {
                    response.sendRedirect("meals");
                    break;
                }
                int mealId = parseIdParam(id);
                Logger.info("try to edit meal with id: " + mealId);
                Meal meal = repo.getMeal(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(InsertOrDelete).forward(request, response);
                break;
            }
            case "create": {
                Logger.info("try to create new meal");
                request.getRequestDispatcher(InsertOrDelete).forward(request, response);
            }
            default: {
                Logger.info("try to view meals");
                request.setAttribute("meals",
                        MealsUtil.filteredByStreams(repo.getMeals(), LocalTime.MIN, LocalTime.MAX, CaloriesPerDay));
                request.getRequestDispatcher(ListMeals).forward(request, response);
            }
        }
    }

    private int parseIdParam(String id) {
        return Integer.parseInt(id);
    }
}
