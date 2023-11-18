import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Please enter exercise number:");


        try {
            int exNum = Integer.parseInt(bufferedReader.readLine());

            switch (exNum) {
                case 2 -> changeCasing_02();
                case 3 -> containsEmployee_03();
                case 4 -> employeesWithSalaryOver50000_04();
                case 5 -> employeesFromDepartment_05();
                case 6 -> addingNewAddressAndUpdatingEmployee_06();
                case 7 -> addressWithEmployeeCount_07();
                case 8 -> getEmployeesWithProject_08();
                case 9 -> findTheLatest10Projects_09();
                case 10 -> increaseSalaries_10();
                case 11 -> findEmployeeByFirstName_11();
                case 12 -> employeesMaximumSalaries_12();
                case 13 -> removeTowns_13();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void removeTowns_13() {
        System.out.println("Please enter town name:");
        String townName = new Scanner(System.in).nextLine();


        entityManager.getTransaction().begin();

        Town town = entityManager.createQuery("FROM Town "
                        + "WHERE name = :town", Town.class)
                .setParameter("town", townName)
                .getSingleResult();

        List<Address> addressesToDelete = entityManager
                .createQuery("FROM Address WHERE town.id= :id", Address.class)
                .setParameter("id", town.getId())
                .getResultList();

        addressesToDelete
                .forEach(t -> t.getEmployees()
                        .forEach(em -> em.setAddress(null)));

        addressesToDelete.forEach(entityManager::remove);
        entityManager.remove(town);

        int countDeletedAddresses = addressesToDelete.size();

        System.out.printf("%d address%s in %s deleted",
                countDeletedAddresses,
                countDeletedAddresses == 1 ? "" : "es",
                townName);

        entityManager.getTransaction().commit();
    }


    private void employeesMaximumSalaries_12() {
        entityManager.createQuery("SELECT department.name, max(salary) " +
                        "FROM Employee " +
                        "GROUP BY department.name " +
                        " HAVING max(salary) NOT BETWEEN 30000 AND 70000", Object[].class)
                .getResultList()
                .forEach(objects -> System.out.println(objects[0] + " " + objects[1]));

    }

    private void findEmployeeByFirstName_11() {
        System.out.println("Please enter letters to search by:");
        entityManager.createQuery("FROM Employee "
                        + "WHERE firstName LIKE CONCAT(:chars, '%')", Employee.class)
                .setParameter("chars", new Scanner(System.in).nextLine())
                .getResultList()
                .forEach(employee -> System.out.printf("%s %s - %s - ($%s)%n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle(),
                        employee.getSalary()));

    }

    private void increaseSalaries_10() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Employee e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department.id IN :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager.getTransaction().commit();

        List<String> DEPARTMENTS_TO_BE_INCREASED =
                List.of("Engineering",
                        "Tool Design",
                        "Marketing",
                        "Information Services");


        List<Employee> employees = entityManager
                .createQuery("FROM Employee WHERE department.name in (:ids)", Employee.class)
                .setParameter("ids", DEPARTMENTS_TO_BE_INCREASED)
                .getResultList();

        for (Employee employee : employees) {
            System.out.printf("%s %s (%s)%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getSalary());
        }
    }

    private void findTheLatest10Projects_09() {
        entityManager.createQuery("FROM Project p " +
                        "ORDER BY p.startDate DESC ", Project.class)
                .setMaxResults(10)
                .getResultList().forEach(project ->
                        System.out.printf("Project name: %s%n" +
                                        "Project Description: %s%n" +
                                        "Project Start Date: %s%n" +
                                        "Project End Date %s%n", project.getName(),
                                project.getDescription(),
                                project.getStartDate(),
                                project.getEndDate()));

    }

    private void getEmployeesWithProject_08() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter employee id:");
        int id = Integer.parseInt(scanner.nextLine());
        Employee employee = entityManager.find(Employee.class, id);

        System.out.printf("%s %s - %s%n%s"
                , employee.getFirstName(), employee.getLastName(), employee.getJobTitle()
                , employee.getProjects().stream().map(Project::getName)
                        .sorted()
                        .collect(Collectors.joining(System.lineSeparator())));

    }

    private void addressWithEmployeeCount_07() {
        List<Address> addressList = entityManager.createQuery("SELECT a FROM Address a " +
                        "ORDER BY a.employees.size DESC ", Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address address : addressList) {
            System.out.printf("%s, %s - %d employees%n", address.getText(),
                    address.getTown() == null
                            ? "Unknown" : address.getTown().getName(),
                    address.getEmployees().size());
        }
    }

    private void addingNewAddressAndUpdatingEmployee_06() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter employee last name:");
        String lastName = scanner.nextLine();


        Employee employee = entityManager.createQuery("SELECT e from Employee e " + "WHERE e.lastName = :l_name ", Employee.class).setParameter("l_name", lastName).getSingleResult();

        Address address = createAddress("Vitoshka 15");

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);

        entityManager.getTransaction().commit();

        return address;
    }

    private void employeesFromDepartment_05() {

        entityManager.createQuery("FROM Employee WHERE department.name = :dName ORDER BY salary, id", Employee.class).setParameter("dName", "Research and Development").getResultList().forEach(employee -> System.out.printf("%s %s from %s - $%.2f%n", employee.getFirstName(), employee.getLastName(), employee.getDepartment().getName(), employee.getSalary()));
    }


    private void employeesWithSalaryOver50000_04() {
        entityManager.createQuery("SELECT e FROM Employee e " + "WHERE salary > 50000", Employee.class).getResultList().forEach(employee -> System.out.println(employee.getFirstName()));
    }

    private void containsEmployee_03() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter employee full name:");
        String fullName = scanner.nextLine();


        String isEmployeePresent = entityManager.createQuery("FROM Employee e where concat_ws(' ', e.firstName, e.lastName) = :full_Name", Employee.class).setParameter("full_Name", fullName).getResultList().isEmpty() ? "No" : "Yes";

        System.out.println(isEmployeePresent);
    }

    private void changeCasing_02() {
        int nameLength = 5;

        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("UPDATE Town t " + "SET t.name = upper(t.name) " + "WHERE length(t.name) <= :name_Length").setParameter("name_Length", nameLength);

        System.out.println(query.executeUpdate());

        entityManager.getTransaction().commit();

    }


}
