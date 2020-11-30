package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

public class AdminUIControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminUIController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Test
    void changeEnabled() throws Exception {
        User disabled = UserTestData.getDisabled();
        perform(MockMvcRequestBuilders.post(REST_URL + "checked")
                .param("id", String.valueOf(USER_ID))
                .param("enabled", "false"))
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userService.get(USER_ID), disabled);
    }

}
