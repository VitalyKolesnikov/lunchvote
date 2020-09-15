package org.example.lunchvote.web.menu;

import org.example.lunchvote.model.Menu;
import org.example.lunchvote.testdata.MenuTestData;
import org.example.lunchvote.to.MenuTo;
import org.example.lunchvote.util.exception.NotFoundException;
import org.example.lunchvote.web.AbstractControllerTest;
import org.example.lunchvote.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.example.lunchvote.TestUtil.readFromJson;
import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.testdata.MenuTestData.*;
import static org.example.lunchvote.testdata.RestaurantTestData.*;
import static org.example.lunchvote.testdata.UserTestData.ADMIN;
import static org.example.lunchvote.util.exception.ErrorType.VALIDATION_ERROR;
import static org.example.lunchvote.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_MENU_RESTAURANT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuRestController.REST_URL + '/';

    @Autowired
    private AdminMenuRestController controller;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENU1));
    }

    @Test
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?date=" + TODAY)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENUS));
    }

    @Test
    void getByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "restaurant/" + KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(KFC_MENUS));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Menu aNew = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new MenuTo(aNew)))
                .with(userHttpBasic(ADMIN)));

        Menu created = readFromJson(action, Menu.class);
        int newId = created.id();
        aNew.setId(newId);
        MENU_MATCHER.assertMatch(created, aNew);
        MENU_MATCHER.assertMatch(controller.get(newId), aNew);
    }

    @Test
    void update() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MENU1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new MenuTo(updated)))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(controller.get(MENU1_ID), updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> controller.get(MENU1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Menu duplicate = new Menu(null, KFC, TODAY);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new MenuTo(duplicate)))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_MENU_RESTAURANT));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Menu duplicate = new Menu(MENU1_ID, BK, TODAY);
        perform(MockMvcRequestBuilders.put(REST_URL + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new MenuTo(duplicate)))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_MENU_RESTAURANT));
    }
}