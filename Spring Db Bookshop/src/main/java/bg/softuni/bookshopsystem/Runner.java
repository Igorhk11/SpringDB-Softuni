package bg.softuni.bookshopsystem;

import bg.softuni.bookshopsystem.domain.entities.Book;
import bg.softuni.bookshopsystem.services.AuthorService;
import bg.softuni.bookshopsystem.services.BookService;
import bg.softuni.bookshopsystem.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Runner implements CommandLineRunner {

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;

    public Runner(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();

//        printAllBooksAfter2000(2000);
//        printAllAuthorsNamesWithBooksReleaseDateAfter1990(1990);
//        printAllAuthorsAndNumbersOfTheirBooks();
        printAllBooksFromAuthorOrderByReleaseDate();

    }

    private void printAllBooksFromAuthorOrderByReleaseDate() {
        bookService.findAllBooksFromAuthorOrderByReleaseDate("George", "Powell")
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumbersOfTheirBooks() {
        authorService.findAllAuthorsOrderBySizeOfBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksReleaseDateAfter1990(int year) {
        bookService.findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);

    }

    private void printAllBooksAfter2000(int year) {
        bookService.findAllBooksAfterYear(2000)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
