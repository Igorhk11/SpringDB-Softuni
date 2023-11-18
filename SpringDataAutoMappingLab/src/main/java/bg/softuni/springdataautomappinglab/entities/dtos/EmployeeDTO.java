package bg.softuni.springdataautomappinglab.entities.dtos;

import java.math.BigDecimal;

public class EmployeeDTO {

    private String firstName;
    private String lastName;
    private double salary;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String firstName, String lastName, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
