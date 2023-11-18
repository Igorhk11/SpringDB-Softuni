package com.example.advquerying.services;


import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    private IngredientsRepository ingredientsRepository;

    public IngredientsServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    public List<String> findIngredientBasedOnLetter(String letter) {
        return ingredientsRepository.findAllByNameStartingWith(letter)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }
}
