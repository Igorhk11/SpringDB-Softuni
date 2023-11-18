package com.example.softunigamestore;

import com.example.softunigamestore.services.GameService;
import com.example.softunigamestore.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.softunigamestore.constants.Commands.*;

@Component
public class Runner implements CommandLineRunner {
    private static final Scanner SCANNER= new Scanner(System.in);
    private UserService userService;
    private GameService gameService;

    public Runner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Please enter your command:");
        String input = SCANNER.nextLine();

        while (!input.equals(CLOSE)) {
            String[] arguments = input.split("\\|");
            String command = arguments[0];

            final String output = switch (command) {
                case REGISTER_USER -> this.userService.registerUser(arguments);
                case LOG_IN_USER -> this.userService.loginUser(arguments);
                case LOGOUT_USER -> this.userService.logoutUser();
                case ADD_GAME -> this.gameService.addGame(arguments);
                case EDIT_GAME -> this.gameService.editGame(arguments);
                case DELETE_GAME -> this.gameService.deleteGame(arguments);
                default -> "Not a valid command, please enter a different command";
            };

            System.out.println(output);


            input = SCANNER.nextLine();

        }
    }

}
