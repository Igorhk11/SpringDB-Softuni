package com.example.advquerying.services;


import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService{
    private ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }


    @Override
    public List<Shampoo> findShampooByGivenSizeOrderById(Size size) {
        return shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findShampooByGivenSizeAndIdOrderByPrice(Size size, Long id) {
        return shampooRepository.findAllBySizeOrIdOrderByPrice(size , id);
    }

    @Override
    public List<Shampoo> findShampooSortedByGivenPrice(BigDecimal price) {
        return shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int findShampoosCountByPrice(BigDecimal price) {
        return shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findShampooByIngredients(List<String> ingredientsName) {
       return shampooRepository.findShampooByIngredients(ingredientsName);
    }
}
