package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {

    List<Shampoo> findShampooByGivenSizeOrderById(Size size);

    List<Shampoo> findShampooByGivenSizeAndIdOrderByPrice(Size size, Long id);

    List<Shampoo> findShampooSortedByGivenPrice(BigDecimal price);

    int findShampoosCountByPrice(BigDecimal price);

    List<Shampoo> findShampooByIngredients(List<String> ingredientsName);
}
