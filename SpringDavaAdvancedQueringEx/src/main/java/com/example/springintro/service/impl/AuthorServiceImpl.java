package com.example.springintro.service.impl;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.repository.AuthorRepository;
import com.example.springintro.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<String> findAuthorByNameEnding(String endStr) {
        return authorRepository.findAllByFirstNameEndingWith(endStr)
                .stream()
                .map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTotalBookCopies() {
        return authorRepository.findAll()
                .stream().map(author ->
                    String.format("%s %s - %d", author.getFirstName(), author.getLastName(),
                            author.getBooks().stream().map(Book::getCopies)
                                    .reduce(Integer::sum)
                                    .orElse(0)))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
