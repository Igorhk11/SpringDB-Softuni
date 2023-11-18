package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContaining(String title);

    List<Book> findAllByAuthor_LastNameStartsWith(String lastName);

    @Query("SELECT count(b) FROM Book b " +
            "WHERE length(b.title) > :length")
    int findAllByTitleGreaterThan(int length);


    List<Book> findAllByTitle(String titleName);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :copiesAdded where b.releaseDate > :date")
    int updateBooksCopies(int copiesAdded , LocalDate date);

    Optional<List<Book>> findAllByReleaseDateAfter(LocalDate date);
}
