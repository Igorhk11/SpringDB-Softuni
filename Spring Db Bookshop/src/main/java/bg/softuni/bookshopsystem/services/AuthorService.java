package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> findAllAuthorsOrderBySizeOfBooks();
}
