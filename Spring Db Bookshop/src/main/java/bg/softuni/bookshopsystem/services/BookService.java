package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksFromAuthorOrderByReleaseDate(String firstName, String lastName);
}
