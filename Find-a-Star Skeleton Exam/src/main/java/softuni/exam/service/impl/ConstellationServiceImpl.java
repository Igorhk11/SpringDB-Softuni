package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private static final String CONSTELLATION_FILE_PATH = "src/main/resources/files/json/constellations.json";
    private ConstellationRepository constellationRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtil validationUtil;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATION_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();

        ConstellationSeedDto[] constellationSeedDtos = gson.fromJson(readConstellationsFromFile(), ConstellationSeedDto[].class);

        Arrays.stream(constellationSeedDtos).filter(constellationSeedDto -> {
            boolean isValid = validationUtil.isValid(constellationSeedDto);


            Optional<Constellation> constByName = constellationRepository.findByName(constellationSeedDto.getName());

            if (constByName.isPresent()) {
                isValid = false;
            }

            sb
                    .append(isValid ? String.format("Successfully imported constellation %s - %s",constellationSeedDto.getName(),
                            constellationSeedDto.getDescription())
                            : "Invalid constellation").append(System.lineSeparator());

            return isValid;

        }).map(constellationSeedDto -> modelMapper.map(constellationSeedDto, Constellation.class)).forEach(constellationRepository::save);

        return sb.toString().trim();
    }
}
