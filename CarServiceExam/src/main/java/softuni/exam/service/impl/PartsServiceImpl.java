package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartsSeedDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PartsServiceImpl implements PartsService {
    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";
    private PartsRepository partRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;

    public PartsServiceImpl(PartsRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();

        PartsSeedDto[] partsSeedDtos = gson.fromJson(readPartsFileContent(), PartsSeedDto[].class);

        Arrays.stream(partsSeedDtos).filter(partsSeedDto -> {
            boolean isValid = validationUtil.isValid(partsSeedDto);

            Optional<Part> partByName = partRepository.findByPartName(partsSeedDto.getPartName());

            if (partByName.isPresent()) {
                isValid = false;
            }

            sb.append(isValid ? String.format("Successfully imported part %s - %s",partsSeedDto.getPartName(), partsSeedDto.getPrice())
                    : "Invalid part").append(System.lineSeparator());

            return isValid;

        }).map(partsSeedDto -> modelMapper.map(partsSeedDto, Part.class))
                .forEach(partRepository::save);

        return sb.toString();
    }
}
