package bg.softuni.SpringAccounts.services;

import bg.softuni.SpringAccounts.models.User;
import bg.softuni.SpringAccounts.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());

        if (byUsername.isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        userRepository.save(user);
    }
}
