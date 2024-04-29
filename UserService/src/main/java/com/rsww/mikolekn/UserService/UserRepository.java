package com.rsww.mikolekn.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static final Map<String, String> users = new HashMap<>();

    static {
        initializeUsers();
    }

    private static void initializeUsers() {
        users.put("user1", "password1");
        users.put("user2", "password2");
        users.put("user3", "password3");
        users.put("user4", "password4");
        users.put("user5", "password5");
        users.put("user6", "password6");
        users.put("user7", "password7");
        users.put("user8", "password8");
        users.put("user9", "password9");
    }

    public static boolean userExists(String input_username, String input_password) {
        String password = users.get(input_username);
        return password != null && password.equals(input_password);
    }

    public static boolean userExists(LoginRequest input_user) {
        String password = users.get(input_user.getUsername());
        return password != null && password.equals(input_user.getPassword());
    }
}
