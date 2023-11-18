package com.example.softunigamestore.services.Impl;

import com.example.softunigamestore.domain.entities.Game;
import com.example.softunigamestore.domain.models.GameAddDto;
import com.example.softunigamestore.domain.models.GameEditDto;
import com.example.softunigamestore.repositories.GameRepository;
import com.example.softunigamestore.services.GameService;
import com.example.softunigamestore.services.UserService;
import com.example.softunigamestore.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;
    private ModelMapper mapper;
    private final ValidationUtil validationUtil;

    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper mapper, ValidationUtil validationUtil, UserService userService) {
        this.gameRepository = gameRepository;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.mapper = new ModelMapper();
    }

    @Override
    public String addGame(String[] arguments) {
        if (!this.userService.isLoggedUserAdmin()) return "Logged user is not admin.";

        String title = arguments[1];
        BigDecimal price = new BigDecimal(arguments[2]);
        Double size = Double.parseDouble(arguments[3]);
        String trailer = arguments[4];
        String thumbnailURL = arguments[5];
        String description = arguments[6];
        String releaseDate = arguments[7];

        GameAddDto gameAddDto = new GameAddDto(title, price, size, trailer, thumbnailURL, description,releaseDate);

        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.violation(gameAddDto);

        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return "Game cannot be added due to constraint violations.";
        }

        Game game = mapper.map(gameAddDto, Game.class);

        game.setReleaseDate(LocalDate.parse(gameAddDto.getReleaseDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        game.setImageThumbnail(gameAddDto.getThumbnailURL());
        this.gameRepository.saveAndFlush(game);

        return "Added " + gameAddDto.getTitle();

    }

    @Override
    public String editGame(String[] arguments) {
        if (!this.userService.isLoggedUserAdmin()) return "Logged user is not admin.";

        Optional<Game> gameToBeEdited = this.gameRepository.findById(Long.valueOf(arguments[1]));

        if (gameToBeEdited.isEmpty()) return "No such game, please enter valid game.";

        Map<String, String> updatingArguments = new HashMap<>();

        for (int i = 2; i < arguments.length; i++) {
            String[] argumentsForUpdate = arguments[i].split("=");
            updatingArguments.put(argumentsForUpdate[0], argumentsForUpdate[1]);
        }

        final GameEditDto gameEditDto = this.mapper.map(gameToBeEdited.get(), GameEditDto.class);

        gameEditDto.updateFields(updatingArguments);

        Game gameToBeSaved = this.mapper.map(gameEditDto, Game.class);

        this.gameRepository.saveAndFlush(gameToBeSaved);

        return "Edited " + gameEditDto.getTitle();
    }

    @Override
    public String deleteGame(String[] arguments) {
        if (!userService.isLoggedUserAdmin()) return "Logged user is not admin";

        Optional<Game> gamesToBeDeleted = gameRepository.findById(Long.valueOf(arguments[1]));

        if (gamesToBeDeleted.isEmpty())return "No such game, please enter valid game ID";

        this.gameRepository.delete(gamesToBeDeleted.get());

        return "Deleted " + gamesToBeDeleted.get().getTitle();

    }


}
