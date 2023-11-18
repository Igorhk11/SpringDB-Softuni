package bg.softuni.jsonexercise.services.impl;

import bg.softuni.jsonexercise.domain.entities.Category;
import bg.softuni.jsonexercise.domain.models.CategorySeedDto;
import bg.softuni.jsonexercise.repositories.CategoryRepository;
import bg.softuni.jsonexercise.services.CategoryService;
import bg.softuni.jsonexercise.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static bg.softuni.jsonexercise.constants.Paths.CATEGORIES_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ValidationUtil validationUtil;

    private ModelMapper modelMapper;
    private Gson gson;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedData() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(CATEGORIES_FILE_PATH));

        CategorySeedDto[] categorySeedDtos = gson.fromJson(fileContent, CategorySeedDto[].class);


        Arrays.stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);

    }

    @Override
    public Set<Category> randomCategories() {
        Set<Category> categorieSet = new HashSet<>();
        int catCount = ThreadLocalRandom.current().nextInt(1, 3);
        long totalCategoriesCount = this.categoryRepository.count() + 1;

        for (int i = 0; i < catCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, totalCategoriesCount);
            categorieSet.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categorieSet;
    }
}
