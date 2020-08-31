package org.example.lunchvote.testdata;

import org.example.lunchvote.TestMatcher;
import org.example.lunchvote.model.Role;
import org.example.lunchvote.model.User;
import org.example.lunchvote.web.json.JsonUtil;

import java.util.Collections;

import static org.example.lunchvote.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(User.class);

    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int USER3_ID = START_SEQ + 2;
    public static final int USER4_ID = START_SEQ + 3;
    public static final int USER5_ID = START_SEQ + 4;
    public static final int USER6_ID = START_SEQ + 5;
    public static final int ADMIN_ID = START_SEQ + 6;

    public static final User USER1 = new User(USER1_ID, "User1", "user1@gmail.com", "pass1", Role.USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@gmail.com", "pass2", Role.USER);
    public static final User USER3 = new User(USER3_ID, "User3", "user3@gmail.com", "pass3", Role.USER);
    public static final User USER4 = new User(USER4_ID, "User4", "user4@gmail.com", "pass4", Role.USER);
    public static final User USER5 = new User(USER5_ID, "User5", "user5@gmail.com", "pass5", Role.USER);
    public static final User USER6 = new User(USER6_ID, "User6", "user6@gmail.com", "pass6", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "pass_admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}