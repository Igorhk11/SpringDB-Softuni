package bg.softuni.bookshopsystem.repositories;

import bg.softuni.bookshopsystem.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


    @Query("SELECT a from Author a ORDER BY SIZE(a.books) DESC")
    List<Author> findAllByBooksSizeDESC();

}
