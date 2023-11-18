package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.entities.Category;
import bg.softuni.bookshopsystem.repositories.CategoryRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_PATH = "C:\\Users\\Igor HK\\IdeaProjects\\BookshopSystem\\src\\main\\resources\\dbContent\\categories.txt";

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(categoryRow -> {
                    Category category = new Category(categoryRow);

                    categoryRepository.save(category);
                });
    }

    @Override
    public Set<Category> getRandomCategories() {

        Set<Category> categories = new HashSet<>();
        int randInt = ThreadLocalRandom.current().nextInt(1, 3);

        long catRepoCount = categoryRepository.count();

        for (int i = 0; i < randInt; i++) {
            long randId = ThreadLocalRandom.current().nextLong(1, catRepoCount + 1);

            Category category = categoryRepository.findById(randId).orElse(null);


            categories.add(category);
        }

        return categories;
    }
}
