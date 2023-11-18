package bg.softuni.SpringAccounts;

import bg.softuni.SpringAccounts.models.User;
import bg.softuni.SpringAccounts.services.AccountService;
import bg.softuni.SpringAccounts.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final AccountService accountService;

    private final UserService userService;


    public ConsoleRunner(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
//        User someuser = new User("someuser", 20);
//        userService.registerUser(someuser);

        accountService.depositMoney(BigDecimal.valueOf(100), 1L);
    }
}
