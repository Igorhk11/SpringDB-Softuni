package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportForecastSeedDto;
import softuni.exam.models.dto.ForecastXMLRootSeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastServiceImpl implements ForecastService {
    private static final String FORECASTS_FILE_PATH = "src/main/resources/files/xml/forecasts.xml";
    private XmlParser xmlParser;
    private ForecastRepository forecastRepository;
    private ModelMapper modelMapper;

    private ValidationUtil validationUtil;
    private CityRepository cityRepository;

    public ForecastServiceImpl(XmlParser xmlParser, ForecastRepository forecastRepository, ModelMapper modelMapper, ValidationUtil validationUtil, CityRepository cityRepository) {
        this.xmlParser = xmlParser;
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean areImported() {
        return forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        ForecastXMLRootSeedDto forecastXMLRootSeedDto = xmlParser.fromFile(FORECASTS_FILE_PATH, ForecastXMLRootSeedDto.class);

        forecastXMLRootSeedDto.getForecasts().stream().filter(forecastSeedDto -> {
            boolean isValid = validationUtil.isValid(forecastSeedDto);

            City city = cityRepository.findCityById(forecastSeedDto.getCity());

            if (city == null) {
                isValid = false;
            }

            Forecast forecast = this.forecastRepository.findAllByCityAndDayOfWeek(Optional.ofNullable(city), forecastSeedDto.getDayOfWeek());

            if (forecast != null) {
                isValid = false;
            }

            sb.append(isValid ? String.format("Successfully import forecast %s - %.2f",
                    forecastSeedDto.getDayOfWeek(), forecastSeedDto.getMaxTemperature())
                    : "Invalid forecast");

            return isValid;


        }).map(forecastSeedDto -> {
            Forecast forecast = modelMapper.map(forecastSeedDto, Forecast.class);
            Optional<City> findById = cityRepository.findById(forecastSeedDto.getCity());
            forecast.setCity(findById.get());

            return forecast;
        }).forEach(forecastRepository::save);

        return sb.toString();
    }

    @Override
    public String exportForecasts() {
        StringBuilder sb = new StringBuilder();

        List<ExportForecastSeedDto> tasks = forecastRepository.findForecastForSundayAndGivenPopulation();


        tasks.forEach(task -> {
            sb.append(String.format("City: %s%n" +
                            "-min temperature: %.2f%n" +
                            "--max temperature: %.2f%n" +
                            "---sunrise: %s%n" +
                            "----sunset: %s%n",
                    task.getCityName(),
                    task.getMinTemperature(),
                    task.getMaxTemperature(),
                    task.getSunrise(),
                    task.getSunset()
            ));

        });

        return sb.toString();
    }
}
