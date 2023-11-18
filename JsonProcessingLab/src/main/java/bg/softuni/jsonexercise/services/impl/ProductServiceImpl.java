package bg.softuni.jsonexercise.services.impl;

import bg.softuni.jsonexercise.domain.entities.Product;
import bg.softuni.jsonexercise.domain.models.ProductNameAndPriceDto;
import bg.softuni.jsonexercise.domain.models.ProductSeedDto;
import bg.softuni.jsonexercise.repositories.ProductRepository;
import bg.softuni.jsonexercise.services.CategoryService;
import bg.softuni.jsonexercise.services.ProductService;
import bg.softuni.jsonexercise.services.UserService;
import bg.softuni.jsonexercise.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.jsonexercise.constants.Paths.PRODUCTS_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private UserService userService;
    private CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedData() throws IOException {
        if (productRepository.count() > 0) {
            return;
        }
        String fileContent = Files.readString(Path.of(PRODUCTS_FILE_PATH));

        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.findRandom());
                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                        product.setBuyer(userService.findRandom());
                    }
                    product.setCategories(categoryService.randomCategories());
                    return product;
                }).forEach(productRepository::save);

    }

    @Override
    public List<ProductNameAndPriceDto> findProductInRangeOrderByPrice(BigDecimal lower, BigDecimal upper) {
      return   productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(lower, upper)
                .stream().map(product -> {
                    ProductNameAndPriceDto productNameAndPriceDto = modelMapper.map(product, ProductNameAndPriceDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));

                    return productNameAndPriceDto;
                }).collect(Collectors.toList());
    }
}
