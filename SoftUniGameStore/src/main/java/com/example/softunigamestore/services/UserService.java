package com.example.softunigamestore.services;

import java.util.List;

public interface UserService {
    String registerUser(String[] arguments);

    String loginUser(String[] arguments);

    String logoutUser();

    boolean isLoggedUserAdmin();
}
