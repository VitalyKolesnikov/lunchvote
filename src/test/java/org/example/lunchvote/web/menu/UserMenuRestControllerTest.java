package org.example.lunchvote.web.menu;

import org.example.lunchvote.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.testdata.MenuTestData.MENUS;
import static org.example.lunchvote.testdata.MenuTestData.MENU_MATCHER;
import static org.example.lunchvote.testdata.UserTestData.USER1;
import static org.example.lunchvote.web.menu.UserMenuRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserMenuRestControllerTest extends AbstractControllerTest {

    @Test
    void getTodays() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENUS));
    }

    @Test
    void getTodaysUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}