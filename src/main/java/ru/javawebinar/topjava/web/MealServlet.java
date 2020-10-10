package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;
    private ConfigurableApplicationContext ctx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("init MealServlet");
        ctx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        log.info("App beans: {}", Arrays.deepToString(ctx.getBeanDefinitionNames()));
        controller = ctx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            controller.create(meal);
        } else {
            controller.update(meal, Integer.parseInt(id));
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "filter":
                LocalDate dFrom = LocalDate.MIN;
                LocalDate dTo = LocalDate.MAX;
                LocalTime tFrom = LocalTime.MIN;
                LocalTime tTo = LocalTime.MAX;
                String dateFrom = request.getParameter("dateFrom");
                String dateTo = request.getParameter("dateTo");
                String timeFrom = request.getParameter("timeFrom");
                String timeTo = request.getParameter("timeTo");
                if (dateFrom != null && !dateFrom.isEmpty()) {
                    dFrom = LocalDate.parse(dateFrom);
                }
                if (dateTo != null && !dateTo.isEmpty()) {
                    dTo = LocalDate.parse(dateTo);
                }
                if (timeFrom != null && !timeFrom.isEmpty()) {
                    tFrom = LocalTime.parse(timeFrom);
                }
                if (timeTo != null && !timeTo.isEmpty()) {
                    tTo = LocalTime.parse(timeTo);
                }
                log.info("Get meals for period [dateFrom: {}, dateTo: {}], [timeFrom: {}, timeTo: {}]",
                        dateFrom, dateTo, timeFrom, timeTo);
                request.setAttribute("meals",
                        controller.getAllFiltered(dFrom, dTo, tFrom, tTo));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        ctx.close();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
