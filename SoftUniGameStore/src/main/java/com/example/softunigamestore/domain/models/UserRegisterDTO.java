package com.example.softunigamestore.domain.models;

import java.util.regex.Pattern;

import static com.example.softunigamestore.constants.ErrorMessages.*;
import static com.example.softunigamestore.constants.Validations.EMAIL_PATTERN;
import static com.example.softunigamestore.constants.Validations.PASSWORD_PATTERN;

public class UserRegisterDTO {
    private String email;

    private String password;

    private String confirmPassword;

    private String fullName;

    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        validate();
    }

    private void validate() {

        if (!Pattern.matches(EMAIL_PATTERN, this.email)) {
            throw new IllegalArgumentException(INVALID_EMAIL);
        }

        if (!Pattern.matches(PASSWORD_PATTERN,this.password)) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }

        if (!this.password.equals(this.confirmPassword)) {
            throw new IllegalArgumentException(PASS_MISS_MATCH);
        }

    }

    public String successfullyRegisteredUser() {
        return fullName + " was registered";
    }

    public UserRegisterDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
