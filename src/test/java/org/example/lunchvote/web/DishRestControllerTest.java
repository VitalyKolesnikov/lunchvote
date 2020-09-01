package org.example.lunchvote.web;

import org.example.lunchvote.model.Dish;
import org.example.lunchvote.to.DishTo;
import org.example.lunchvote.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.example.lunchvote.TestUtil.readFromJson;
import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.testdata.DishTestData.*;
import static org.example.lunchvote.testdata.MenuTestData.MENU1;
import static org.example.lunchvote.testdata.UserTestData.ADMIN;
import static org.example.lunchvote.util.exception.ErrorType.VALIDATION_ERROR;
import static org.example.lunchvote.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_DISH_IN_MENU;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    private DishRestController controller;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISH1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
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
        Dish aNew = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new DishTo(aNew)))
                .with(userHttpBasic(ADMIN)));

        Dish created = readFromJson(action, Dish.class);
        int newId = created.id();
        aNew.setId(newId);
        DISH_MATCHER.assertMatch(created, aNew);
        DISH_MATCHER.assertMatch(controller.get(newId), aNew);
    }

    @Test
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH3_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new DishTo(updated)))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(controller.get(DISH3_ID), updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NoSuchElementException.class, () -> controller.get(DISH1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, "q", -150, MENU1);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new DishTo(invalid)))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(DISH1_ID, "q", -150, MENU1);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new DishTo(invalid)))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateInMenu() throws Exception {
        Dish duplicate = new Dish(null, "Chicken wings", 300, MENU1);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new DishTo(duplicate)))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DISH_IN_MENU));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Dish duplicate = new Dish(DISH1_ID, "French Fries", 300, MENU1);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new DishTo(duplicate)))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DISH_IN_MENU));
    }
}