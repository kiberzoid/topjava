package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcOperations jdbcOperations;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final SimpleJdbcInsert insertUser;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcOperations = jdbcTemplate;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("date_time", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number key = insertUser.executeAndReturnKey(parameters);
            meal.setId(key.intValue());
            return meal;
        } else if (namedParameterJdbcOperations.update("UPDATE meals SET date_time=:date_time, description=:description, " +
                "calories=:calories WHERE user_id=:user_id AND id=:id", parameters) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcOperations.update("DELETE from meals where user_id=? and id=?", userId, id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals =
                jdbcOperations.query("SELECT * FROM meals WHERE user_id = ? AND id = ?", ROW_MAPPER, userId, id);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcOperations.query("SELECT * FROM meals " +
                "WHERE user_id = ? " +
                "ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcOperations.query("SELECT * FROM meals " +
                "WHERE user_id = ? AND date_time >= ? AND date_time < ?  " +
                "ORDER BY date_time DESC", ROW_MAPPER, userId, startDateTime, endDateTime);
    }
}
