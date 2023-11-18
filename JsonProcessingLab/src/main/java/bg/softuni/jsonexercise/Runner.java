package bg.softuni.jsonexercise;

import bg.softuni.jsonexercise.domain.models.ProductNameAndPriceDto;
import bg.softuni.jsonexercise.services.CategoryService;
import bg.softuni.jsonexercise.services.ProductService;
import bg.softuni.jsonexercise.services.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static bg.softuni.jsonexercise.constants.Paths.PRODUCT_IN_RANGE_FILE_PATH;

@Component
public class Runner implements CommandLineRunner {

    private static final String PRODUCT_IN_RANGE="product-in-range.json";
    private CategoryService categoryService;
    private UserService userService;

    private final Gson gson;

    private ProductService productService;
    private final BufferedReader bufferedReader;

    public Runner(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        seedDate();

        System.out.println("Enter exercise num:");
        int exNum = Integer.parseInt(bufferedReader.readLine());

        switch (exNum) {
            case 1 -> productInRange();
        }
    }

    private void productInRange() throws IOException {
        List<ProductNameAndPriceDto> productDtos = productService.findProductInRangeOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));

        String content = gson.toJson(productDtos);

       writeToFIle(PRODUCT_IN_RANGE_FILE_PATH + PRODUCT_IN_RANGE,content );
    }

    private void writeToFIle(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath), Collections.singleton(content));
    }

    private void seedDate() throws IOException {
    categoryService.seedData();
    userService.seedData();
    productService.seedData();
    }
}
