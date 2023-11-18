package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportStarsOrderByLightYearsDTO;
import softuni.exam.models.entity.Star;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    Optional<Star> findByName(String startName);


    @Query("select new softuni.exam.models.dto.ExportStarsOrderByLightYearsDTO " +
            " (s.name, s.lightYears, s.description, c.name) " +
            "FROM Star as s " +
            "JOIN Constellation as c on c.id = s.constellation.id " +
            "WHERE s.starType = 'RED_GIANT' and s.astronomers.size = 0 " +
            "ORDER BY s.lightYears asc")
    List<ExportStarsOrderByLightYearsDTO> findAllByStarsOrderByLightYears();

}
