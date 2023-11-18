package bg.softuni.bookshopsystem.repositories;

import bg.softuni.bookshopsystem.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
