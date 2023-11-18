package com.example.softunigamestore.services.Impl;

import com.example.softunigamestore.domain.entities.User;
import com.example.softunigamestore.domain.models.UserLoginDTO;
import com.example.softunigamestore.domain.models.UserRegisterDTO;
import com.example.softunigamestore.repositories.UserRepository;
import com.example.softunigamestore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.softunigamestore.constants.ErrorMessages.EMAIL_ALREADY_EXISTS;

@Service
public class UserServiceImpl implements UserService {
    private User loggedInUser;


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String registerUser(String[] arguments) {
        String email = arguments[1];
        String password = arguments[2];
        String confirmPassword = arguments[3];
        String fullName = arguments[4];

        UserRegisterDTO userRegisterDto;

        try {
            userRegisterDto = new UserRegisterDTO(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }

        if (this.userRepository.findFirstByEmail(userRegisterDto.getEmail()).isPresent()) {
            return EMAIL_ALREADY_EXISTS;
        }

        User user = this.modelMapper.map(userRegisterDto, User.class);


        if (userRepository.count() == 0) {
            user.setAdmin(true);
        } else {
            user.setAdmin(false);
        }

        this.userRepository.saveAndFlush(user);

        return userRegisterDto.successfullyRegisteredUser();
    }

    @Override
    public String loginUser(String[] arguments) {

        if (this.loggedInUser != null) return "User is already logged in";

        String email = arguments[1];
        String password = arguments[2];

        final Optional<User> userToBeLogged = userRepository.findFirstByEmail(email);

        if (userToBeLogged.isEmpty()) return "Incorrect user";

        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);

        try {
            userLoginDTO.validate(userToBeLogged.get().getPassword());
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }

        loggedInUser = userToBeLogged.get();

        return userLoginDTO.successfullyLogged();
    }

    @Override
    public String logoutUser() {
        if (loggedInUser == null) return "Cannot log out. No user was logged in.";

        String name = loggedInUser.getFullName();
        this.loggedInUser = null;

        return "User " + name + " successfully logged out";

    }

    @Override
    public boolean isLoggedUserAdmin() {
        return this.loggedInUser != null && this.loggedInUser.getAdmin();
    }
}
