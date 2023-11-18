package softuni.exam.service;



import softuni.exam.models.entities.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {

    boolean areImported();

    String readCarsFileContent() throws IOException;
	
	String importCars() throws IOException;

    String getCarsOrderByPicturesCountThenByMake();

    Car findById(Long id);

}
