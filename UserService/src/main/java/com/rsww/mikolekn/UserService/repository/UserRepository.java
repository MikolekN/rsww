package com.rsww.mikolekn.UserService.repository;

import com.rsww.mikolekn.UserService.dto.LoginRequest;

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

    public static boolean userExists(String inputUsername, String inputPassword) {
        String password = users.get(inputUsername);
        return password != null && password.equals(inputPassword);
    }

    public static boolean userExists(LoginRequest inputUser) {
        return userExists(inputUser.getUsername(), inputUser.getPassword());
    }
}
