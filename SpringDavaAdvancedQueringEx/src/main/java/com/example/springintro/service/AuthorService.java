package com.example.springintro.service;

import com.example.springintro.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    List<String> findAuthorByNameEnding(String endStr);

    List<String> findTotalBookCopies();
}
