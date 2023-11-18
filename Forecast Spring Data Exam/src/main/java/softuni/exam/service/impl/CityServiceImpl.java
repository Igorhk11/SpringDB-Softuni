package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CitiesSeedDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private static final String CITIES_FILE_PATH = "src/main/resources/files/json/cities.json";
    private CityRepository cityRepository;

    private ModelMapper modelMapper;

    private Gson gson;

    private ValidationUtil validationUtil;

    private CountryRepository countryRepository;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();

        CitiesSeedDTO[] citiesSeedDTOS = gson.fromJson(readCitiesFileContent(), CitiesSeedDTO[].class);

        Arrays.stream(citiesSeedDTOS).filter(citiesSeedDTO -> {
            boolean isValid = validationUtil.isValid(citiesSeedDTO);

            Optional<City> findByCityName = cityRepository.findByCityName(citiesSeedDTO.getCityName());

            if (findByCityName.isPresent()) {
                isValid = false;
            }

            sb
                    .append(isValid ? String.format("Successfully imported city %s - %d",citiesSeedDTO.getCityName()
                    ,citiesSeedDTO.getPopulation())
                            : "Invalid city").append(System.lineSeparator());

            return isValid;

        }).map(citiesSeedDTO -> {
            City city = modelMapper.map(citiesSeedDTO, City.class);
            Optional<Country> countryById = countryRepository.findById(citiesSeedDTO.getCountry());
            city.setCountry(countryById.get());
            return city;
        }).forEach(cityRepository::save);

        return sb.toString();
    }
}
