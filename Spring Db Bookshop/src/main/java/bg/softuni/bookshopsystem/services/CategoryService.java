package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
