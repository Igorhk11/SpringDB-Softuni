package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarsRootSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CarsServiceImpl implements CarsService {
    private CarsRepository carRepository;
    private ModelMapper modelMapper;
    private XmlParser xmlParser;
    private ValidationUtil validationUtil;

    public CarsServiceImpl(CarsRepository carRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        CarsRootSeedDto carsRootSeedDto = xmlParser.fromFile(CARS_FILE_PATH, CarsRootSeedDto.class);

        carsRootSeedDto.getCars().stream().filter(carSeedDTO -> {
            boolean isValid = validationUtil.isValid(carSeedDTO);

            Optional<Car> findByPlateNumber = carRepository.findByPlateNumber(carSeedDTO.getPlateNumber());

            if (findByPlateNumber.isPresent()) {
                isValid = false;
            }

            sb.
            append(isValid ? String.format("Successfully imported car %s - %s", carSeedDTO.getCarMake(), carSeedDTO.getCarModel())
                    : "Invalid car").append(System.lineSeparator());


            return isValid;

        }).map(carSeedDTO -> modelMapper.map(carSeedDTO, Car.class)).forEach(carRepository::save);

        return sb.toString().trim();

    }
}
