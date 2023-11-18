package bg.softuni.jsonexercise.services;

import bg.softuni.jsonexercise.domain.entities.User;

import java.io.IOException;

public interface UserService {
    void seedData() throws IOException;

    User findRandom();
}
