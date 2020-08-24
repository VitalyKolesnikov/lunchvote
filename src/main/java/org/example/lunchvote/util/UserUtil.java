package org.example.lunchvote.util;

import org.example.lunchvote.model.Role;
import org.example.lunchvote.model.User;
import org.example.lunchvote.to.UserTo;

import java.time.Instant;

public final class UserUtil {
    private UserUtil() {
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User of(UserTo userTo) {
        return new User(userTo.getId(), userTo.getName(), userTo.getEmail(), userTo.getPassword(), Role.USER);
    }

}