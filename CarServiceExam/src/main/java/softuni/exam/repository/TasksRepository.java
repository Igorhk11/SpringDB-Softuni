package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

//    @Query("SELECT new softuni.exam.models.dto.ExportHighestPriceTask" +
//            "(t.id, t.price, c.carMake, c.carModel, c.engine, concat(m.firstName, ' ', m.lastName), c.kilometers) " +
//            "FROM Task t " +
//            "JOIN Car c ON c.id = t.car.id " +
//            "JOIN Mechanic m ON m.id = t.mechanic.id " +
//            "WHERE c.carType = 'coupe' " +
//            "ORDER BY t.price DESC")
//    List<ExportHighestPriceTask> findHighestTaskPrice();

    List<Task> findAllByCarCarTypeOrderByPriceDesc(CarType carType);

}
