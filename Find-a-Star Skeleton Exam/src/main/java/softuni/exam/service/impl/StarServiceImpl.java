package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportStarsOrderByLightYearsDTO;
import softuni.exam.models.dto.StarSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StarServiceImpl implements StarService {

    private static final String STAR_FILE_PATH = "src/main/resources/files/json/stars.json";
    private  StarRepository starRepository;

    private ValidationUtil validationUtil;
    private Gson gson;
    private ModelMapper modelMapper;

    private ConstellationRepository constellationRepository;

    public StarServiceImpl(StarRepository starRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper, ConstellationRepository constellationRepository) {
        this.starRepository = starRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.constellationRepository = constellationRepository;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STAR_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder sb = new StringBuilder();

        StarSeedDto[] starSeedDtos = gson.fromJson(readStarsFileContent(), StarSeedDto[].class);

        Arrays.stream(starSeedDtos).filter(starSeedDto -> {
            boolean isValid = validationUtil.isValid(starSeedDto);

            Optional<Star> starByName = starRepository.findByName(starSeedDto.getName());

            if (starByName.isPresent()) {
                isValid = false;
            }

            sb.append(isValid ? String.format("Successfully imported star %s - %.2f light years",
                    starSeedDto.getName(), starSeedDto.getLightYears())
                    : "Invalid star").append(System.lineSeparator());

            return isValid;
        }).map(starSeedDto -> {
            Star star = modelMapper.map(starSeedDto, Star.class);

            Optional<Constellation> constById = this.constellationRepository.findById(starSeedDto.getConstellation());
            star.setConstellation(constById.get());

            return star;

        }).forEach(starRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String exportStars() {
        StringBuilder sb = new StringBuilder();

        List<ExportStarsOrderByLightYearsDTO> exportStar = starRepository.findAllByStarsOrderByLightYears();

        exportStar.forEach(star -> {
            sb.append(String.format("Star: %s%n" +
                            "   *Distance: %.2f light years%n" +
                            "   **Description: %s%n" +
                            "   ***Constellation: %s%n",
                    star.getName(),
                    star.getLightYears(),
                    star.getDescription(),
                    star.getConstellation()));
        });

        return sb.toString();
    }
}
