package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.entities.Author;
import bg.softuni.bookshopsystem.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final static String AUTHORS_FILE_PATH = "C:\\Users\\Igor HK\\IdeaProjects\\BookshopSystem\\src\\main\\resources\\dbContent\\authors.txt";
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 1) {
            return;
        }

        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .stream()
                .forEach(authorRow -> {
                    String[] fullName = authorRow.split("\\s+");

                    Author author = new Author(fullName[0], fullName[1]);

                    authorRepository.save(author);

                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom.current().nextLong(1, authorRepository.count() + 1);

        return authorRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<String> findAllAuthorsOrderBySizeOfBooks() {
        return authorRepository.findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d", author.getFirstName(), author.getLastName(), author.getBooks().size()))
                .collect(Collectors.toList());
    }
}
