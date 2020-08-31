package org.example.lunchvote.testdata;

import org.example.lunchvote.TestMatcher;
import org.example.lunchvote.model.Restaurant;

import java.util.List;

import static org.example.lunchvote.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Restaurant.class);

    public static final int KFC_ID = START_SEQ + 7;
    public static final int  BK_ID = START_SEQ + 8;
    public static final int MCD_ID = START_SEQ + 9;

    public static final Restaurant KFC = new Restaurant(KFC_ID, "KFC");
    public static final Restaurant BK = new Restaurant(BK_ID, "Burger King");
    public static final Restaurant MCD = new Restaurant(MCD_ID, "McDonald`s");

    public static final List<Restaurant> RESTAURANTS = List.of(KFC, BK, MCD);

    public static Restaurant getNew() {
        return new Restaurant(null, "Black Star Burger");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(KFC_ID, "Kentucky Fried Chicken");
    }
}