package bg.softuni.jsonexercise.services.impl;

import bg.softuni.jsonexercise.domain.entities.User;
import bg.softuni.jsonexercise.domain.models.UserSeedDto;
import bg.softuni.jsonexercise.repositories.UserRepository;
import bg.softuni.jsonexercise.services.UserService;
import bg.softuni.jsonexercise.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static bg.softuni.jsonexercise.constants.Paths.USERS_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    private Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public void seedData() throws IOException {
        if (userRepository.count() > 0) {
            return;
        }
        String fileContent = Files.readString(Path.of(USERS_FILE_PATH));

        UserSeedDto[] userSeedDtos = gson.fromJson(fileContent, UserSeedDto[].class);

        Arrays.stream(userSeedDtos)
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User findRandom() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);


    }
}
