package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByFirstNameEndingWith(String endStr);


    @Query("SELECT a.firstName, a.lastName,  SUM(b.copies) as totalCopies " +
            "FROM Author a " +
            "JOIN a.books b " +
            "GROUP BY a " +
            "ORDER BY totalCopies DESC")
    List<Author> findTotalBookCopiesByAuthor();
}
