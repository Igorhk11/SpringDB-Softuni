package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Please select exercise number:");

        int exNum = Integer.parseInt(bufferedReader.readLine());

        switch (exNum) {
            case 1 -> booksTitlesByAgeRestriction();
            case 2 -> goldenBooks();
            case 3 -> booksByPrice();
            case 4 -> notReleasedBooks();
            case 5 -> booksReleasedBeforeDate();
            case 6 -> authorsSearch();
            case 7 -> booksSearch();
            case 8 -> bookTitlesSearch();
            case 9 -> countBooks();
            case 10 -> totalBookCopies();
            case 11 -> reducedBook();
            case 12 -> increaseBookCopies();
        }


    }

    private void increaseBookCopies() throws IOException {

        System.out.println("Please insert data in format dd MMM yyyy:");
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));

        System.out.println("Please select the number of book copies:");
        int copies = Integer.parseInt(bufferedReader.readLine());

        int count = bookService.increaseBookCopiesOnGivenDateByProviderNumber(localDate, copies);
        System.out.println(count);

    }

    private void reducedBook() throws IOException {

        System.out.println("Please enter book title name:");
        String bookTitle = bufferedReader.readLine();


        bookService.findBookInfoByBookTitle(bookTitle).forEach(System.out::println);
    }

    private void totalBookCopies() {

        authorService.findTotalBookCopies()
                .forEach(System.out::println);
    }

    private void countBooks() throws IOException {

        System.out.println("Please enter book title length:");

        int length = Integer.parseInt(bufferedReader.readLine());

        int count = bookService.findCountBooksByTitleLength(length);
        System.out.println(count);

    }

    private void bookTitlesSearch() throws IOException {

        System.out.println("Please enter author last name that you wish to check:");

        String lastName = bufferedReader.readLine();

        bookService.findAllBookTitlesByAuthorLastNameContaining(lastName).forEach(System.out::println);
    }

    private void booksSearch() throws IOException {
        System.out.println("Please enter string that you wish to check:");

        String input = bufferedReader.readLine().toLowerCase();

        bookService.findBooksTitleByContainingString(input)
                .forEach(System.out::println);
    }

    private void authorsSearch() throws IOException {
        System.out.println("Please enter name ending:");

        String endStr = bufferedReader.readLine();

        authorService.findAuthorByNameEnding(endStr)
                .forEach(System.out::println);
    }

    private void booksReleasedBeforeDate() throws IOException {

        System.out.println("Please enter your data in dd-MM-yyyy format:");
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findAllBooksReleasedBeforeGivenDate(localDate)
                .forEach(System.out::println);

    }

    private void notReleasedBooks() throws IOException {

        System.out.println("Please select release year:");

        Integer year = Integer.parseInt(bufferedReader.readLine());

        bookService.findBooksNotReleasedInGivenYear(year)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    private void booksByPrice() {

        bookService.findBooksByPriceLowerThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(book -> System.out.println(book.getTitle() + " " + book.getPrice()));
    }

    private void goldenBooks() {

        bookService.findGoldenBooksWhichHaveLessThanGivenCopies().
                forEach(book -> System.out.println(book.getTitle()));

    }

    private void booksTitlesByAgeRestriction() throws IOException {

        System.out.println("Please enter Age Restriction: ");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase());

        bookService.findAllBooksByAgeRestriction(ageRestriction)
                .forEach(book -> System.out.println(book.getTitle()));
    }
}
