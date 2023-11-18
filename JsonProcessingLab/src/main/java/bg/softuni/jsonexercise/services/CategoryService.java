package bg.softuni.jsonexercise.services;

import bg.softuni.jsonexercise.domain.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedData() throws IOException;

    Set<Category> randomCategories();

}
