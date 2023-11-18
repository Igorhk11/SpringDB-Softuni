package com.example.softunigamestore.domain.models;

import static com.example.softunigamestore.constants.ErrorMessages.PASS_MISS_MATCH;

public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void validate(String realPassword) {
        if (!this.password.equals(realPassword)) {
            throw new IllegalArgumentException(PASS_MISS_MATCH);
        }
    }

    public String successfullyLogged() {
        return "Successfully logged in " + this.email;
    }
}
