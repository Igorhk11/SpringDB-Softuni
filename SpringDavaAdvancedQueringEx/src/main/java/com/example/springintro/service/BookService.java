package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {

    List<Book> findAllBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findGoldenBooksWhichHaveLessThanGivenCopies();

    List<Book> findBooksByPriceLowerThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);

    List<Book> findBooksNotReleasedInGivenYear(Integer year);

    List<String> findAllBooksReleasedBeforeGivenDate(LocalDate date);

    List<String> findBooksTitleByContainingString(String input);

    List<String> findAllBookTitlesByAuthorLastNameContaining(String lastName);

    int findCountBooksByTitleLength(int length);

    List<String> findBookInfoByBookTitle(String bookTitle);

    int increaseBookCopiesOnGivenDateByProviderNumber(LocalDate localDate, int copies);
}
