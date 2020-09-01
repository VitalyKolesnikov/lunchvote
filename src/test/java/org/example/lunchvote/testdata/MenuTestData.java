package org.example.lunchvote.testdata;

import org.example.lunchvote.TestMatcher;
import org.example.lunchvote.model.Menu;
import org.example.lunchvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.model.AbstractBaseEntity.START_SEQ;
import static org.example.lunchvote.testdata.RestaurantTestData.*;

public class MenuTestData {

    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Menu.class, "dishes");

    public static LocalDate TODAY = LocalDate.now();

    public static final int MENU1_ID = START_SEQ + 10;
    public static final int MENU2_ID = START_SEQ + 11;
    public static final int MENU3_ID = START_SEQ + 12;

    public static final Menu MENU1 = new Menu(MENU1_ID, KFC, TODAY);
    public static final Menu MENU2 = new Menu(MENU2_ID, BK, TODAY);
    public static final Menu MENU3 = new Menu(MENU3_ID, MCD, TODAY);

    public static List<Menu> MENUS = List.of(MENU1, MENU2, MENU3);

    public static Menu getNew() {
        return new Menu(null, KFC, TODAY.plusDays(1));
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, KFC, TODAY.plusDays(2));
    }
}