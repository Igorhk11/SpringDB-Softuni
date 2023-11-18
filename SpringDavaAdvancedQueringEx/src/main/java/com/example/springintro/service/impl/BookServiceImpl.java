package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }



    @Override
    public List<Book> findAllBooksByAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findAllByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> findGoldenBooksWhichHaveLessThanGivenCopies() {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);

    }

    @Override
    public List<Book> findBooksByPriceLowerThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lower , upper);
    }

    @Override
    public List<Book> findBooksNotReleasedInGivenYear(Integer year) {
        LocalDate lower = LocalDate.of(year, 1 , 1);
        LocalDate upper = LocalDate.of(year, 12 , 31);
        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(lower, upper);
    }

    @Override
    public List<String> findAllBooksReleasedBeforeGivenDate(LocalDate date) {
        return bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(book -> String.format("%s %s %.2f", book.getTitle(),
                        book.getEditionType(),
                        book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBooksTitleByContainingString(String input) {
        return bookRepository.findAllByTitleContaining(input)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookTitlesByAuthorLastNameContaining(String lastName) {
        return bookRepository.findAllByAuthor_LastNameStartsWith(lastName)
                .stream()
                .map(book -> String.format("%s (%s %s)", book.getTitle(),
                        book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public int findCountBooksByTitleLength(int length) {
        return bookRepository.findAllByTitleGreaterThan(length);
    }

    @Override
    public List<String> findBookInfoByBookTitle(String bookTitle) {
        return bookRepository.findAllByTitle(bookTitle).stream()
                .map(book -> String.format("%s %s %s %.2f", book.getTitle(), book.getEditionType(),
                        book.getAgeRestriction(), book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public int increaseBookCopiesOnGivenDateByProviderNumber(LocalDate localDate, int copies) {

        List<Book> books = bookRepository.findAllByReleaseDateAfter(localDate).get();

        books.forEach(book -> book.setCopies(book.getCopies() + copies));

        bookRepository.saveAllAndFlush(books);
        return copies * books.size();

    }

}
