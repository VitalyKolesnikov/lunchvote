package org.example.lunchvote.testdata;

import org.example.lunchvote.TestMatcher;
import org.example.lunchvote.model.Dish;

import static org.example.lunchvote.model.AbstractBaseEntity.START_SEQ;
import static org.example.lunchvote.testdata.MenuTestData.MENU1;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class, "menu");

    public static final int DISH1_ID = START_SEQ + 13;
    public static final int DISH3_ID = START_SEQ + 15;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Chicken wings", 300, MENU1);

    public static Dish getNew() {
        return new Dish(null, "Shrimp roll", 300, MENU1);
    }

    public static Dish getUpdated() {
        return new Dish(DISH3_ID, "Coca-cola light", 95, MENU1);
    }
}