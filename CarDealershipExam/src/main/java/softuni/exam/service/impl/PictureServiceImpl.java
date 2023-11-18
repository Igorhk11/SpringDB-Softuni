package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PictureSeedDto;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {
    private static final String PICTURES_FILE_PATH = "src/main/resources/files/json/pictures.json";
    private PictureRepository pictureRepository;

    private CarService carService;

    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        PictureSeedDto[] pictureSeedDtos = gson.fromJson(readPicturesFromFile(),PictureSeedDto[].class);
        StringBuilder sb = new StringBuilder();

        for (PictureSeedDto pictureSeedDto : pictureSeedDtos) {
            boolean isValid = validationUtil.isValid(pictureSeedDto);

            if (isValid) {
                sb.append(String.format("Successfully import picture - %s", pictureSeedDto.getName()));
            } else {
                sb.append("Invalid picture");
            }

            sb.append(System.lineSeparator());
        }

        Arrays.stream(pictureSeedDtos).filter(validationUtil::isValid)
                .map(pictureSeedDto -> {
                    Picture picture = modelMapper.map(pictureSeedDto, Picture.class);
                    picture.setCar(carService.findById(pictureSeedDto.getCar()));

                    return picture;
                })
                .forEach(pictureRepository::save);


        return sb.toString();
    }
}
