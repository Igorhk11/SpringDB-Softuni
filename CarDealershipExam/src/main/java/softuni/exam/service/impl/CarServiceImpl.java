package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final String CAR_FILE_PATH = "src/main/resources/files/json/cars.json";
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CAR_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

        CarSeedDto[] carSeedDtos = gson.fromJson(readCarsFileContent(), CarSeedDto[].class);


        for (CarSeedDto carSeedDto : carSeedDtos) {
            boolean isValid = validationUtil.isValid(carSeedDto);

            if (isValid) {
                sb.append(String.format("Successfully imported car - %s - %s", carSeedDto.getMake(), carSeedDto.getModel()));
            } else {
                sb.append("Invalid car");
            }

            sb.append(System.lineSeparator());
        }

        Arrays.stream(carSeedDtos).filter(validationUtil::isValid).map(carSeedDto -> modelMapper.map(carSeedDto, Car.class))
                .forEach(carRepository::save);


        return sb.toString();
}

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {

        StringBuilder sb = new StringBuilder();

        carRepository.findCarsOrderByPicturesCountThenByMake()
                .forEach(car -> {
                    sb.append(String.format("Car make - %s, model - %s\n" +
                                            "\tKilometers - %d\n" +
                                            "\tRegistered on - %s\n" +
                                            "\tNumber of pictures - %d\n",
                                    car.getMake(), car.getModel(), car.getKilometers(),
                                    car.getRegisteredOn(), car.getPictures().size()))
                            .append(System.lineSeparator());
                });

        return sb.toString().trim();
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }
}
