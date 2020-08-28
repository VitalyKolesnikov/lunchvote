package org.example.lunchvote;

import org.example.lunchvote.model.Role;
import org.example.lunchvote.model.User;

import java.util.Collections;

import static org.example.lunchvote.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class);

    public static final int NOT_FOUND = 10;
    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int USER3_ID = START_SEQ + 2;
    public static final int ADMIN_ID = START_SEQ + 3;

    public static final User USER1 = new User(USER1_ID, "User1", "user1@gmail.com", "{noop}pass1", Role.USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@gmail.com", "{noop}pass2", Role.USER);
    public static final User USER3 = new User(USER3_ID, "User3", "user3@gmail.com", "{noop}pass3", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "{noop}pass_admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}