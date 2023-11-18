package com.example.advquerying.services;

import java.util.List;

public interface IngredientsService {

    List<String> findIngredientBasedOnLetter(String letter);
}
