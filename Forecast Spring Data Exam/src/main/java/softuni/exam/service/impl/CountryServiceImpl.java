package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountriesSeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String COUNTRY_FILE_PATH = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRY_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountriesSeedDto[] countriesSeedDtos = gson.fromJson(readCountriesFromFile(), CountriesSeedDto[].class);

        Arrays.stream(countriesSeedDtos).filter(countriesSeedDto -> {
            boolean isValid = validationUtil.isValid(countriesSeedDto);

            Optional<Country> findByCountryName = countryRepository.findByCountryName(countriesSeedDto.getCountryName());

            if (findByCountryName.isPresent()) {
                isValid = false;
            }

            sb.append(isValid ? String.format("Successfully imported country %s - %s",countriesSeedDto.getCountryName()
                    ,countriesSeedDto.getCurrency())
                    : "Invalid country").append(System.lineSeparator());

            return isValid;

        }).map(countriesSeedDto -> modelMapper.map(countriesSeedDto, Country.class)).forEach(countryRepository::save);
        return sb.toString();
    }
}
