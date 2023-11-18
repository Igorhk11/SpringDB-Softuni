package bg.softuni.springdataautomappinglab;

import bg.softuni.springdataautomappinglab.entities.Address;
import bg.softuni.springdataautomappinglab.entities.dtos.CreateAddressDTO;
import bg.softuni.springdataautomappinglab.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    private AddressService addressService;

    @Autowired
    public Runner(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        createEmployee(scanner);
    }

    private void createEmployee(Scanner scanner) {
        String firstName = scanner.nextLine();
        BigDecimal salary = new BigDecimal(scanner.nextLine());
        LocalDate birthday = LocalDate.parse(scanner.nextLine());


    }
}
