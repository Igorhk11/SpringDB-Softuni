package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomersRootXMLDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private static final String ASTRONOMERS_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";
    private AstronomerRepository astronomerRepository;

    private XmlParser xmlParser;

    private ValidationUtil validationUtil;

    private ModelMapper modelMapper;

    private StarRepository starRepository;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, StarRepository starRepository) {
        this.astronomerRepository = astronomerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.starRepository = starRepository;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        AstronomersRootXMLDto astronomersRootXMLDto = xmlParser.fromFile(ASTRONOMERS_FILE_PATH, AstronomersRootXMLDto.class);

        astronomersRootXMLDto.getAstronomer().stream().filter(astronomersXMLDto -> {
            boolean isValid = validationUtil.isValid(astronomersXMLDto);

            Optional<Astronomer> astronomerByFullName = astronomerRepository.findByFirstNameAndLastName(astronomersXMLDto.getFirstName(), astronomersXMLDto.getLastName());

            if (astronomerByFullName.isPresent()) {
                isValid = false;
            }

            Optional<Star> findById = starRepository.findById(astronomersXMLDto.getObservingStarId());

            if (findById.isEmpty()) {
                isValid = false;
            }

            sb
                    .append(isValid ? String.format("Successfully imported astronomer %s %s - %.2f",
                            astronomersXMLDto.getFirstName(),
                            astronomersXMLDto.getLastName(),
                            astronomersXMLDto.getAverageObservationHours())
                            :"Invalid astronomer").append(System.lineSeparator());


                return isValid;
        }).map(astronomersXMLDto -> {
            Astronomer astronomer = modelMapper.map(astronomersXMLDto, Astronomer.class);

            Optional<Star> findById = starRepository.findById(astronomersXMLDto.getObservingStarId());
            astronomer.setObservingStar(findById.get());

            return astronomer;

        }).forEach(astronomerRepository::save);


        return sb.toString().trim();
    }
}
