package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientsService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private IngredientsService ingredientsService;
    private ShampooService shampooService;

    public Runner(IngredientsService ingredientsService, ShampooService shampooService) {
        this.ingredientsService = ingredientsService;
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {


//        printShampooByGivenSizeOrderById();
//        printShampooByGivenSizeAndIdOrderByPrice();
//        printShampooSortedByGivenPrice();
//        printIngredientsBasedOnGivenLetter();
//        printCountShampoosByPrice();
        printShampooByIngredientName();

    }

    private void printShampooByIngredientName() {
        shampooService.findShampooByIngredients(List.of("Berry", "Mineral-Collagen"))
                .forEach(shampoo -> System.out.println(shampoo.getBrand()));
    }


    private void printCountShampoosByPrice() {
        shampooService.findShampoosCountByPrice(BigDecimal.valueOf(8.50));
    }

    private void printIngredientsBasedOnGivenLetter() {
        ingredientsService.findIngredientBasedOnLetter("M")
                .forEach(System.out::println);
    }

    private void printShampooSortedByGivenPrice() {
        shampooService.findShampooSortedByGivenPrice(BigDecimal.valueOf(5))
                .forEach(shampoo -> System.out.println(shampoo.getBrand() + " " + shampoo.getPrice()));
    }

    private void printShampooByGivenSizeAndIdOrderByPrice() {
        shampooService.findShampooByGivenSizeAndIdOrderByPrice(Size.MEDIUM, 10L)
                .forEach(shampoo -> System.out.println(shampoo.getBrand() + shampoo.getSize() + shampoo.getPrice()));
    }

    private void printShampooByGivenSizeOrderById() {
        shampooService.findShampooByGivenSizeOrderById(Size.MEDIUM).
                forEach(shampoo -> {
                    System.out.println(shampoo.getBrand() + " " + shampoo.getPrice());
                });
    }
}
